package com.github.hamlet_rt.workoutapp.common.repo

import com.github.hamlet_rt.workoutapp.common.models.WrkTng
import com.github.hamlet_rt.workoutapp.common.models.WrkTngId
import com.github.hamlet_rt.workoutapp.common.models.WrkTngLock

data class DbTngIdRequest(
    val id: WrkTngId,
    val lock: WrkTngLock = WrkTngLock.NONE,
) {
    constructor(tng: WrkTng): this(tng.id, tng.lock)
}
