package com.github.hamlet_rt.workoutapp.blackbox.test.action.v1

import io.kotest.assertions.asClue
import com.github.hamlet_rt.workoutapp.api.v1.models.TngCreateObject
import com.github.hamlet_rt.workoutapp.api.v1.models.TngCreateRequest
import com.github.hamlet_rt.workoutapp.api.v1.models.TngCreateResponse
import com.github.hamlet_rt.workoutapp.api.v1.models.TngResponseObject
import com.github.hamlet_rt.workoutapp.blackbox.fixture.client.Client
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe


suspend fun Client.createTng(tng: TngCreateObject = someCreateTng): TngResponseObject = createTng(tng) {
    it should haveSuccessResult
    it.tng shouldNotBe null
    it.tng?.apply {
        title shouldBe tng.title
        description shouldBe tng.description
        tngType shouldBe tng.tngType
        visibility shouldBe tng.visibility
    }
    it.tng!!
}

suspend fun <T> Client.createTng(tng: TngCreateObject = someCreateTng, block: (TngCreateResponse) -> T): T =
    withClue("createTngV1: $tng") {
        val response = sendAndReceive(
            "tng/create", TngCreateRequest(
                requestType = "create",
                debug = debug,
                tng = tng
            )
        ) as TngCreateResponse

        response.asClue(block)
    }
