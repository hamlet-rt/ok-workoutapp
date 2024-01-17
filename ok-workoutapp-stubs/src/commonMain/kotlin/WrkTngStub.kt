package com.github.hamlet_rt.workoutapp.stubs

import com.github.hamlet_rt.workoutapp.common.models.*
import com.github.hamlet_rt.workoutapp.stubs.WrkTngStubBolts.TNG_POWER_FULL_BODY

object WrkTngStub {
    fun get() = WrkTng(
        id = WrkTngId("1"),
        title = "Силовая тренировка",
        description = "Силовая тренировка на все тело",
        ownerId = WrkUserId("user-1"),
        tngType = WrkTngType.POWER,
        visibility = WrkVisibility.VISIBLE_PUBLIC,
        permissionsClient = mutableSetOf(
            WrkTngPermissionClient.READ,
            WrkTngPermissionClient.UPDATE,
            WrkTngPermissionClient.DELETE,
            WrkTngPermissionClient.MAKE_VISIBLE_PUBLIC,
            WrkTngPermissionClient.MAKE_VISIBLE_GROUP,
            WrkTngPermissionClient.MAKE_VISIBLE_OWNER,
        )
    )

    fun prepareResult(block: WrkTng.() -> Unit): WrkTng = get().apply(block)

    fun prepareSearchList(filter: String, type: WrkTngType) = listOf(
        wrkTngExercise("p-01", filter, type),
        wrkTngExercise("p-02", filter, type),
        wrkTngExercise("p-03", filter, type),
        wrkTngExercise("p-04", filter, type),
        wrkTngExercise("p-05", filter, type),
        wrkTngExercise("p-06", filter, type),
    )

    private fun wrkTngExercise(id: String, filter: String, type: WrkTngType) =
        wrkTng(get(), id = id, filter = filter, type = type)

    private fun wrkTng(base: WrkTng, id: String, filter: String, type: WrkTngType) = base.copy(
        id = WrkTngId(id),
        title = "$filter $id",
        description = "desc $filter $id",
        tngType = type,
    )

}
