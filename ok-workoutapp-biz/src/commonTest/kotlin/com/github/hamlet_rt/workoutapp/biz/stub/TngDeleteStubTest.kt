package com.github.hamlet_rt.workoutapp.biz.stub

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import com.github.hamlet_rt.workoutapp.biz.WrkTngProcessor
import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.models.*
import com.github.hamlet_rt.workoutapp.common.stubs.WrkStubs
import com.github.hamlet_rt.workoutapp.stubs.WrkTngStub
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class TngDeleteStubTest {

    private val processor = WrkTngProcessor()
    val id = WrkTngId("666")

    @Test
    fun delete() = runTest {

        val ctx = WrkContext(
            command = WrkCommand.DELETE,
            state = WrkState.NONE,
            workMode = WrkWorkMode.STUB,
            stubCase = WrkStubs.SUCCESS,
            tngRequest = WrkTng(
                id = id,
            ),
        )
        processor.exec(ctx)

        val stub = WrkTngStub.get()
        assertEquals(stub.id, ctx.tngResponse.id)
        assertEquals(stub.title, ctx.tngResponse.title)
        assertEquals(stub.description, ctx.tngResponse.description)
        assertEquals(stub.tngType, ctx.tngResponse.tngType)
        assertEquals(stub.visibility, ctx.tngResponse.visibility)
    }

    @Test
    fun badId() = runTest {
        val ctx = WrkContext(
            command = WrkCommand.DELETE,
            state = WrkState.NONE,
            workMode = WrkWorkMode.STUB,
            stubCase = WrkStubs.BAD_ID,
            tngRequest = WrkTng(),
        )
        processor.exec(ctx)
        assertEquals(WrkTng(), ctx.tngResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = WrkContext(
            command = WrkCommand.DELETE,
            state = WrkState.NONE,
            workMode = WrkWorkMode.STUB,
            stubCase = WrkStubs.DB_ERROR,
            tngRequest = WrkTng(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(WrkTng(), ctx.tngResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = WrkContext(
            command = WrkCommand.DELETE,
            state = WrkState.NONE,
            workMode = WrkWorkMode.STUB,
            stubCase = WrkStubs.BAD_TITLE,
            tngRequest = WrkTng(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(WrkTng(), ctx.tngResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
