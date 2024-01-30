package com.github.hamlet_rt.workoutapp.blackbox.test.action.v1

import com.github.hamlet_rt.workoutapp.api.v1.models.TngResponseObject
import com.github.hamlet_rt.workoutapp.api.v1.models.TngSearchFilter
import com.github.hamlet_rt.workoutapp.api.v1.models.TngSearchRequest
import com.github.hamlet_rt.workoutapp.api.v1.models.TngSearchResponse
import com.github.hamlet_rt.workoutapp.blackbox.fixture.client.Client
import io.kotest.assertions.withClue
import io.kotest.assertions.asClue
import io.kotest.matchers.should

suspend fun Client.searchTng(search: TngSearchFilter): List<TngResponseObject> = searchAd(search) {
    it should haveSuccessResult
    it.tngs ?: listOf()
}

suspend fun <T> Client.searchAd(search: TngSearchFilter, block: (TngSearchResponse) -> T): T =
    withClue("searchTngV1: $search") {
        val response = sendAndReceive(
            "tng/search",
            TngSearchRequest(
                requestType = "search",
                debug = debug,
                tngFilter = search,
            )
        ) as TngSearchResponse

        response.asClue(block)
    }