package com.github.hamlet_rt.workoutapp.mappers.v1

import com.github.hamlet_rt.workoutapp.api.v1.models.*
import org.junit.Test
import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.models.*
import com.github.hamlet_rt.workoutapp.common.stubs.WrkStubs
import kotlin.test.assertEquals

class MapperTest {
    @Test
    fun fromTransport() {
        val req = TngCreateRequest(
            requestId = "1234",
            debug = TngDebug(
                mode = TngRequestDebugMode.STUB,
                stub = TngRequestDebugStubs.SUCCESS,
            ),
            tng = TngCreateObject(
                title = "title",
                description = "desc",
                tngType = TngType.POWER,
                visibility = TngVisibility.PUBLIC,
            ),
        )

        val context = WrkContext()
        context.fromTransport(req)

        assertEquals(WrkStubs.SUCCESS, context.stubCase)
        assertEquals(WrkWorkMode.STUB, context.workMode)
        assertEquals("title", context.tngRequest.title)
        assertEquals(WrkVisibility.VISIBLE_PUBLIC, context.tngRequest.visibility)
        assertEquals(WrkTngType.POWER, context.tngRequest.tngType)
    }

    @Test
    fun toTransport() {
        val context = WrkContext(
            requestId = WrkRequestId("1234"),
            command = WrkCommand.CREATE,
            tngResponse = WrkTng(
                title = "title",
                description = "desc",
                tngType = WrkTngType.POWER,
                visibility = WrkVisibility.VISIBLE_PUBLIC,
            ),
            errors = mutableListOf(
                WrkError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = WrkState.RUNNING,
        )

        val req = context.toTransportTng() as TngCreateResponse

        assertEquals("1234", req.requestId)
        assertEquals("title", req.tng?.title)
        assertEquals("desc", req.tng?.description)
        assertEquals(TngVisibility.PUBLIC, req.tng?.visibility)
        assertEquals(TngType.POWER, req.tng?.tngType)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
    }
}
