package com.github.hamlet_rt.workoutapp.blackbox.test.action.v1

import com.github.hamlet_rt.workoutapp.blackbox.fixture.client.Client
import io.kotest.assertions.withClue
import io.kotest.matchers.should

suspend fun Client.createTng(): Unit =
    withClue("createTngV1") {
        val response = sendAndReceive(
            "tng/create", """
                {
                    "name": "Bolt"
                }
            """.trimIndent()
        )

        response should haveNoErrors
    }
