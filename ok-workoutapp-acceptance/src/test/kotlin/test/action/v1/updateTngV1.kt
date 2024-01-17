package com.github.hamlet_rt.workoutapp.blackbox.test.action.v1


import com.github.hamlet_rt.workoutapp.api.v1.models.TngResponseObject
import com.github.hamlet_rt.workoutapp.api.v1.models.TngUpdateObject
import com.github.hamlet_rt.workoutapp.blackbox.fixture.client.Client
import com.github.hamlet_rt.workoutapp.api.v1.models.*
import com.github.hamlet_rt.workoutapp.blackbox.test.action.beValidId
import com.github.hamlet_rt.workoutapp.blackbox.test.action.beValidLock
import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

suspend fun Client.updateTng(id: String?, lock: String?, tng: TngUpdateObject): TngResponseObject =
    updateTng(id, lock, tng) {
        it should haveSuccessResult
        it.tng shouldNotBe null
        it.tng?.apply {
            if (tng.title != null)
                title shouldBe tng.title
            if (tng.description != null)
                description shouldBe tng.description
            if (tng.tngType != null)
                tngType shouldBe tng.tngType
            if (tng.visibility != null)
                visibility shouldBe tng.visibility
        }
        it.tng!!
    }

suspend fun <T> Client.updateTng(id: String?, lock: String?, tng: TngUpdateObject, block: (TngUpdateResponse) -> T): T =
    withClue("updatedV1: $id, lock: $lock, set: $tng") {
        id should beValidId
        lock should beValidLock

        val response = sendAndReceive(
            "tng/update", TngUpdateRequest(
                requestType = "update",
                debug = debug,
                tng = tng.copy(id = id, lock = lock)
            )
        ) as TngUpdateResponse

        response.asClue(block)
    }
