package com.github.hamlet_rt.workoutapp.common.repo

import com.github.hamlet_rt.workoutapp.common.helpers.errorEmptyId as wrkErrorEmptyId
import com.github.hamlet_rt.workoutapp.common.helpers.errorNotFound as wrkErrorNotFound
import com.github.hamlet_rt.workoutapp.common.helpers.errorRepoConcurrency
import com.github.hamlet_rt.workoutapp.common.models.WrkError
import com.github.hamlet_rt.workoutapp.common.models.WrkTng
import com.github.hamlet_rt.workoutapp.common.models.WrkTngLock

data class DbTngResponse(
    override val data: WrkTng?,
    override val isSuccess: Boolean,
    override val errors: List<WrkError> = emptyList()
): IDbResponse<WrkTng> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbTngResponse(null, true)
        fun success(result: WrkTng) = DbTngResponse(result, true)
        fun error(errors: List<WrkError>, data: WrkTng? = null) = DbTngResponse(data, false, errors)
        fun error(error: WrkError, data: WrkTng? = null) = DbTngResponse(data, false, listOf(error))

        val errorEmptyId = error(wrkErrorEmptyId)

        fun errorConcurrent(lock: WrkTngLock, tng: WrkTng?) = error(
            errorRepoConcurrency(lock, tng?.lock?.let { WrkTngLock(it.asString()) }),
            tng
        )

        val errorNotFound = error(wrkErrorNotFound)
    }
}
