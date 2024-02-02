package com.github.hamlet_rt.workoutapp.springapp.api.v1.controller

import com.github.hamlet_rt.workoutapp.api.v1.models.*
import com.github.hamlet_rt.workoutapp.backend.repo.sql.RepoTngSQL
import com.github.hamlet_rt.workoutapp.biz.WrkTngProcessor
import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.mappers.v1.*
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coVerify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters

@WebFluxTest(TngController::class, ExercisesController::class)
internal class TngControllerTest {
    @Autowired
    private lateinit var webClient: WebTestClient

    @MockkBean(relaxUnitFun = true)
    private lateinit var processor: WrkTngProcessor

    @MockkBean
    private lateinit var repo: RepoTngSQL

    @Test
    fun createTng() = testStubTng(
        "/v1/tng/create",
        TngCreateRequest(),
        WrkContext().toTransportCreate()
    )

    @Test
    fun readTng() = testStubTng(
        "/v1/tng/read",
        TngReadRequest(),
        WrkContext().toTransportRead()
    )

    @Test
    fun updateTng() = testStubTng(
        "/v1/tng/update",
        TngUpdateRequest(),
        WrkContext().toTransportUpdate()
    )

    @Test
    fun deleteTng() = testStubTng(
        "/v1/tng/delete",
        TngDeleteRequest(),
        WrkContext().toTransportDelete()
    )

    @Test
    fun searchTng() = testStubTng(
        "/v1/tng/search",
        TngSearchRequest(),
        WrkContext().toTransportSearch()
    )

    @Test
    fun searchExercises() = testStubTng(
        "/v1/tng/exercises",
        TngExercisesRequest(),
        WrkContext().toTransportExercises()
    )

    private inline fun <reified Req : Any, reified Res : Any> testStubTng(
        url: String,
        requestObj: Req,
        responseObj: Res,
    ) {
        webClient
            .post()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(requestObj))
            .exchange()
            .expectStatus().isOk
            .expectBody(Res::class.java)
            .value {
                println("RESPONSE: $it")
                Assertions.assertThat(it).isEqualTo(responseObj)
            }
        coVerify { processor.exec(any()) }
    }
}
