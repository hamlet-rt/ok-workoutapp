package com.github.hamlet_rt.workoutapp.backend.repo.sql

import com.benasher44.uuid.uuid4
import com.github.hamlet_rt.workoutapp.common.helpers.asWrkError
import com.github.hamlet_rt.workoutapp.common.models.*
import com.github.hamlet_rt.workoutapp.common.repo.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class RepoTngSQL(
    properties: SqlProperties,
    initObjects: Collection<WrkTng> = emptyList(),
    val randomUuid: () -> String = { uuid4().toString() },
) : ITngRepository {

    init {
        val driver = when {
            properties.url.startsWith("jdbc:postgresql://") -> "org.postgresql.Driver"
            else -> throw IllegalArgumentException("Unknown driver for url ${properties.url}")
        }

        Database.connect(
            properties.url, driver, properties.user, properties.password
        )

        transaction {
            if (properties.dropDatabase) SchemaUtils.drop(TngTable)
            SchemaUtils.create(TngTable)
            initObjects.forEach { createTng(it) }
        }
    }

    private fun createTng(tng: WrkTng): WrkTng {
        val res = TngTable.insert {
            to(it, tng, randomUuid)
        }

        return TngTable.from(res)
    }

    private fun <T> transactionWrapper(block: () -> T, handle: (Exception) -> T): T =
        try {
            transaction {
                block()
            }
        } catch (e: Exception) {
            handle(e)
        }

    private fun transactionWrapper(block: () -> DbTngResponse): DbTngResponse =
        transactionWrapper(block) { DbTngResponse.error(it.asWrkError()) }

    override suspend fun createTng(rq: DbTngRequest): DbTngResponse = transactionWrapper {
        DbTngResponse.success(createTng(rq.tng))
    }

    private fun read(id: WrkTngId): DbTngResponse {
        val res = TngTable.select {
            TngTable.id eq id.asString()
        }.singleOrNull() ?: return DbTngResponse.errorNotFound
        return DbTngResponse.success(TngTable.from(res))
    }

    override suspend fun readTng(rq: DbTngIdRequest): DbTngResponse = transactionWrapper { read(rq.id) }

    private fun update(
        id: WrkTngId,
        lock: WrkTngLock,
        block: (WrkTng) -> DbTngResponse
    ): DbTngResponse =
        transactionWrapper {
            if (id == WrkTngId.NONE) return@transactionWrapper DbTngResponse.errorEmptyId

            val current = TngTable.select { TngTable.id eq id.asString() }
                .firstOrNull()
                ?.let { TngTable.from(it) }

            when {
                current == null -> DbTngResponse.errorNotFound
                current.lock != lock -> DbTngResponse.errorConcurrent(lock, current)
                else -> block(current)
            }
        }

    override suspend fun updateTng(rq: DbTngRequest): DbTngResponse =
        update(rq.tng.id, rq.tng.lock) {
            TngTable.update({
                (TngTable.id eq rq.tng.id.asString()) and (TngTable.lock eq rq.tng.lock.asString())
            }) {
                to(it, rq.tng, randomUuid)
            }
            read(rq.tng.id)
        }

    override suspend fun deleteTng(rq: DbTngIdRequest): DbTngResponse = update(rq.id, rq.lock) {
        TngTable.deleteWhere {
            (id eq rq.id.asString()) and (lock eq rq.lock.asString())
        }
        DbTngResponse.success(it)
    }

    override suspend fun searchTng(rq: DbTngFilterRequest): DbTngsResponse =
        transactionWrapper({
            val res = TngTable.select {
                buildList {
                    add(Op.TRUE)
                    if (rq.ownerId != WrkUserId.NONE) {
                        add(TngTable.owner eq rq.ownerId.asString())
                    }
                    if (rq.wrkTngType != WrkTngType.NONE) {
                        add(TngTable.wrkTngType eq rq.wrkTngType)
                    }
                    if (rq.titleFilter.isNotBlank()) {
                        add(
                            (TngTable.title like "%${rq.titleFilter}%")
                                or (TngTable.description like "%${rq.titleFilter}%")
                        )
                    }
                }.reduce { a, b -> a and b }
            }
            DbTngsResponse(data = res.map { TngTable.from(it) }, isSuccess = true)
        }, {
            DbTngsResponse.error(it.asWrkError())
        })
}
