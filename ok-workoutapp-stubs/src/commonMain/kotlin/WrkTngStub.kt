package com.github.hamlet_rt.workoutapp.stubs

import com.github.hamlet_rt.workoutapp.common.models.WrkTng
import com.github.hamlet_rt.workoutapp.common.models.WrkTngId
import com.github.hamlet_rt.workoutapp.common.models.WrkTngType
import com.github.hamlet_rt.workoutapp.stubs.WrkTngStubBolts.TNG_POWER_FULL_BODY

object WrkTngStub {
    fun get(): WrkTng = TNG_POWER_FULL_BODY.copy()

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
        wrkTng(TNG_POWER_FULL_BODY, id = id, filter = filter, type = type)

    private fun wrkTng(base: WrkTng, id: String, filter: String, type: WrkTngType) = base.copy(
        id = WrkTngId(id),
        title = "$filter $id",
        description = "desc $filter $id",
        tngType = type,
    )

}
