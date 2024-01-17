package com.github.hamlet_rt.workoutapp.blackbox.test.action.v1

import com.github.hamlet_rt.workoutapp.blackbox.fixture.client.Client
import com.github.hamlet_rt.workoutapp.api.v1.models.*
import com.github.hamlet_rt.workoutapp.blackbox.test.action.beValidId
import com.github.hamlet_rt.workoutapp.blackbox.test.action.beValidLock
import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

suspend fun Client.deleteTng(id: String?, lock: String?) {
    withClue("deleteTngV1: $id, lock: $lock") {
        id should beValidId
        lock should beValidLock

        val response = sendAndReceive(
            "tng/delete",
            TngDeleteRequest(
                requestType = "delete",
                debug = debug,
                tng = TngDeleteObject(id = id, lock = lock)
            )
        ) as TngDeleteResponse

        response.asClue {
            response should haveSuccessResult
            response.tng shouldNotBe null
            response.tng?.id shouldBe id
        }
    }
}