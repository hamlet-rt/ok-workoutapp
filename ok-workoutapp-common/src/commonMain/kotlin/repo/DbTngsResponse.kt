package com.github.hamlet_rt.workoutapp.common.repo

import com.github.hamlet_rt.workoutapp.common.models.WrkError
import com.github.hamlet_rt.workoutapp.common.models.WrkTng

data class DbTngsResponse(
    override val data: List<WrkTng>?,
    override val isSuccess: Boolean,
    override val errors: List<WrkError> = emptyList(),
): IDbResponse<List<WrkTng>> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbTngsResponse(emptyList(), true)
        fun success(result: List<WrkTng>) = DbTngsResponse(result, true)
        fun error(errors: List<WrkError>) = DbTngsResponse(null, false, errors)
        fun error(error: WrkError) = DbTngsResponse(null, false, listOf(error))
    }
}
