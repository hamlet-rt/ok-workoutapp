package com.github.hamlet_rt.workoutapp.repo.inmemory

import com.benasher44.uuid.uuid4
import com.github.hamlet_rt.workoutapp.common.helpers.errorRepoConcurrency
import com.github.hamlet_rt.workoutapp.repo.inmemory.model.TngEntity
import com.github.hamlet_rt.workoutapp.common.models.*
import com.github.hamlet_rt.workoutapp.common.repo.*
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class TngRepoInMemory(
    initObjects: List<WrkTng> = emptyList(),
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
) : ITngRepository {

    private val cache = Cache.Builder<String, TngEntity>()
        .expireAfterWrite(ttl)
        .build()
    private val mutex: Mutex = Mutex()

    init {
        initObjects.forEach {
            save(it)
        }
    }

    private fun save(tng: WrkTng) {
        val entity = TngEntity(tng)
        if (entity.id == null) {
            return
        }
        cache.put(entity.id, entity)
    }

    override suspend fun createTng(rq: DbTngRequest): DbTngResponse {
        val key = randomUuid()
        val tng = rq.tng.copy(id = WrkTngId(key), lock = WrkTngLock(randomUuid()))
        val entity = TngEntity(tng)
        cache.put(key, entity)
        return DbTngResponse(
            data = tng,
            isSuccess = true,
        )
    }

    override suspend fun readTng(rq: DbTngIdRequest): DbTngResponse {
        val key = rq.id.takeIf { it != WrkTngId.NONE }?.asString() ?: return resultErrorEmptyId
        return cache.get(key)
            ?.let {
                DbTngResponse(
                    data = it.toInternal(),
                    isSuccess = true,
                )
            } ?: resultErrorNotFound
    }

    override suspend fun updateTng(rq: DbTngRequest): DbTngResponse {
        val key = rq.tng.id.takeIf { it != WrkTngId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.tng.lock.takeIf { it != WrkTngLock.NONE }?.asString() ?: return resultErrorEmptyLock
        val newTng = rq.tng.copy(lock = WrkTngLock(randomUuid()))
        val entity = TngEntity(newTng)
        return mutex.withLock {
            val oldTng = cache.get(key)
            when {
                oldTng == null -> resultErrorNotFound
                oldTng.lock != oldLock -> DbTngResponse(
                    data = oldTng.toInternal(),
                    isSuccess = false,
                    errors = listOf(errorRepoConcurrency(WrkTngLock(oldLock), oldTng.lock?.let { WrkTngLock(it) }))
                )

                else -> {
                    cache.put(key, entity)
                    DbTngResponse(
                        data = newTng,
                        isSuccess = true,
                    )
                }
            }
        }
    }

    override suspend fun deleteTng(rq: DbTngIdRequest): DbTngResponse {
        val key = rq.id.takeIf { it != WrkTngId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.lock.takeIf { it != WrkTngLock.NONE }?.asString() ?: return resultErrorEmptyLock
        return mutex.withLock {
            val oldTng = cache.get(key)
            when {
                oldTng == null -> resultErrorNotFound
                oldTng.lock != oldLock -> DbTngResponse(
                    data = oldTng.toInternal(),
                    isSuccess = false,
                    errors = listOf(errorRepoConcurrency(WrkTngLock(oldLock), oldTng.lock?.let { WrkTngLock(it) }))
                )

                else -> {
                    cache.invalidate(key)
                    DbTngResponse(
                        data = oldTng.toInternal(),
                        isSuccess = true,
                    )
                }
            }
        }
    }

    override suspend fun searchTng(rq: DbTngFilterRequest): DbTngsResponse {
        val result = cache.asMap().asSequence()
            .filter { entry ->
                rq.ownerId.takeIf { it != WrkUserId.NONE }?.let {
                    it.asString() == entry.value.ownerId
                } ?: true
            }
            .filter { entry ->
                rq.wrkTngType.takeIf { it != WrkTngType.NONE }?.let {
                    it.name == entry.value.tngType
                } ?: true
            }
            .filter { entry ->
                rq.titleFilter.takeIf { it.isNotBlank() }?.let {
                    entry.value.title?.contains(it) ?: false
                } ?: true
            }
            .map { it.value.toInternal() }
            .toList()
        return DbTngsResponse(
            data = result,
            isSuccess = true
        )
    }

    companion object {
        val resultErrorEmptyId = DbTngResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                WrkError(
                    code = "id-empty",
                    group = "validation",
                    field = "id",
                    message = "Id must not be null or blank"
                )
            )
        )
        val resultErrorEmptyLock = DbTngResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                WrkError(
                    code = "lock-empty",
                    group = "validation",
                    field = "lock",
                    message = "Lock must not be null or blank"
                )
            )
        )
        val resultErrorNotFound = DbTngResponse(
            isSuccess = false,
            data = null,
            errors = listOf(
                WrkError(
                    code = "not-found",
                    field = "id",
                    message = "Not Found"
                )
            )
        )
    }
}
