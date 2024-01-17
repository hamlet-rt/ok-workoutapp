package com.github.hamlet_rt.workoutapp.blackbox.test.action.v1

import com.github.hamlet_rt.workoutapp.api.v1.models.TngReadObject
import com.github.hamlet_rt.workoutapp.api.v1.models.TngReadRequest
import com.github.hamlet_rt.workoutapp.api.v1.models.TngReadResponse
import com.github.hamlet_rt.workoutapp.api.v1.models.TngResponseObject
import com.github.hamlet_rt.workoutapp.blackbox.fixture.client.Client
import com.github.hamlet_rt.workoutapp.blackbox.test.action.beValidId
import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldNotBe

suspend fun Client.readTng(id: String?): TngResponseObject = readTng(id) {
    it should haveSuccessResult
    it.tng shouldNotBe null
    it.tng!!
}

suspend fun <T> Client.readTng(id: String?, block: (TngReadResponse) -> T): T =
    withClue("readTngV1: $id") {
        id should beValidId

        val response = sendAndReceive(
            "tng/read",
            TngReadRequest(
                requestType = "read",
                debug = debug,
                tng = TngReadObject(id = id)
            )
        ) as TngReadResponse

        response.asClue(block)
    }
