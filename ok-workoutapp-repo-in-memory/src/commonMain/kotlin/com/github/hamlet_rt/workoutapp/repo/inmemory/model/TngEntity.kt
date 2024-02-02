package com.github.hamlet_rt.workoutapp.repo.inmemory.model

import com.github.hamlet_rt.workoutapp.common.models.*

data class TngEntity(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val ownerId: String? = null,
    val tngType: String? = null,
    val visibility: String? = null,
    val lock: String? = null,
) {
    constructor(model: WrkTng): this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        title = model.title.takeIf { it.isNotBlank() },
        description = model.description.takeIf { it.isNotBlank() },
        ownerId = model.ownerId.asString().takeIf { it.isNotBlank() },
        tngType = model.tngType.takeIf { it != WrkTngType.NONE }?.name,
        visibility = model.visibility.takeIf { it != WrkVisibility.NONE }?.name,
        lock = model.lock.asString().takeIf { it.isNotBlank() }
    )

    fun toInternal() = WrkTng(
        id = id?.let { WrkTngId(it) }?: WrkTngId.NONE,
        title = title?: "",
        description = description?: "",
        ownerId = ownerId?.let { WrkUserId(it) }?: WrkUserId.NONE,
        tngType = tngType?.let { WrkTngType.valueOf(it) }?: WrkTngType.NONE,
        visibility = visibility?.let { WrkVisibility.valueOf(it) }?: WrkVisibility.NONE,
        lock = lock?.let { WrkTngLock(it) } ?: WrkTngLock.NONE,
    )
}
