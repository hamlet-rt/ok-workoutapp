package com.github.hamlet_rt.workoutapp.backend.repo.sql

import com.github.hamlet_rt.workoutapp.common.models.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder

object TngTable : Table("tng") {
    val id = varchar("id", 128)
    val title = varchar("title", 128)
    val description = varchar("description", 512)
    val owner = varchar("owner", 128)
    val visibility = enumeration("visibility", WrkVisibility::class)
    val wrkTngType = enumeration("wrk_tng_type", WrkTngType::class)
    val lock = varchar("lock", 50)

    override val primaryKey = PrimaryKey(id)

    fun from(res: InsertStatement<Number>) = WrkTng(
        id = WrkTngId(res[id].toString()),
        title = res[title],
        description = res[description],
        ownerId = WrkUserId(res[owner].toString()),
        visibility = res[visibility],
        tngType = res[wrkTngType],
        lock = WrkTngLock(res[lock])
    )

    fun from(res: ResultRow) = WrkTng(
        id = WrkTngId(res[id].toString()),
        title = res[title],
        description = res[description],
        ownerId = WrkUserId(res[owner].toString()),
        visibility = res[visibility],
        tngType = res[wrkTngType],
        lock = WrkTngLock(res[lock])
    )

    fun to(it: UpdateBuilder<*>, tng: WrkTng, randomUuid: () -> String) {
        it[id] = tng.id.takeIf { it != WrkTngId.NONE }?.asString() ?: randomUuid()
        it[title] = tng.title
        it[description] = tng.description
        it[owner] = tng.ownerId.asString()
        it[visibility] = tng.visibility
        it[wrkTngType] = tng.tngType
        it[lock] = tng.lock.takeIf { it != WrkTngLock.NONE }?.asString() ?: randomUuid()
    }

}
