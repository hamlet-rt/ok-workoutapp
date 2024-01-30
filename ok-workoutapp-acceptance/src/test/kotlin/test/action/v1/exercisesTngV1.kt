package com.github.hamlet_rt.workoutapp.blackbox.test.action.v1

import com.github.hamlet_rt.workoutapp.api.v1.models.TngExercisesRequest
import com.github.hamlet_rt.workoutapp.api.v1.models.TngExercisesResponse
import com.github.hamlet_rt.workoutapp.api.v1.models.TngReadObject
import com.github.hamlet_rt.workoutapp.api.v1.models.TngResponseObject
import com.github.hamlet_rt.workoutapp.blackbox.fixture.client.Client
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.assertions.asClue

suspend fun Client.exercisesTng(id: String?): List<TngResponseObject> = exercisesTng(id) {
    it should haveSuccessResult
    it.tngs ?: listOf()
}

suspend fun <T> Client.exercisesTng(id: String?, block: (TngExercisesResponse) -> T): T =
    withClue("searchOffersV1: $id") {
        val response = sendAndReceive(
            "Tng/offers",
            TngExercisesRequest(
                requestType = "offers",
                debug = debug,
                tng = TngReadObject(id = id),
            )
        ) as TngExercisesResponse

        response.asClue(block)
    }
