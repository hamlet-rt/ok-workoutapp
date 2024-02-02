package com.github.hamlet_rt.workoutapp.backend.repo.tests

import com.github.hamlet_rt.workoutapp.common.models.*

abstract class BaseInitTngs(val op: String): IInitObjects<WrkTng> {

    open val lockOld: WrkTngLock = WrkTngLock("20000000-0000-0000-0000-000000000001")
    open val lockBad: WrkTngLock = WrkTngLock("20000000-0000-0000-0000-000000000009")

    fun createInitTestModel(
        suf: String,
        ownerId: WrkUserId = WrkUserId("owner-123"),
        tngType: WrkTngType = WrkTngType.POWER,
        lock: WrkTngLock = lockOld,
    ) = WrkTng(
        id = WrkTngId("tng-repo-$op-$suf"),
        title = "$suf stub",
        description = "$suf stub description",
        ownerId = ownerId,
        visibility = WrkVisibility.VISIBLE_TO_OWNER,
        tngType = tngType,
        lock = lock,
    )
}
