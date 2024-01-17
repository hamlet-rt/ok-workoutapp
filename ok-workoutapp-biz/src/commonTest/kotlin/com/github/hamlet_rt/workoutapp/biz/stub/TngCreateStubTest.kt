package com.github.hamlet_rt.workoutapp.biz.stub

import com.github.hamlet_rt.workoutapp.biz.WrkTngProcessor
import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.models.*
import com.github.hamlet_rt.workoutapp.common.stubs.WrkStubs
import com.github.hamlet_rt.workoutapp.stubs.WrkTngStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class TngCreateStubTest {

    private val processor = WrkTngProcessor()
    val id = WrkTngId("666")
    val title = "title 666"
    val description = "desc 666"
    val tngType = WrkTngType.POWER
    val visibility = WrkVisibility.VISIBLE_PUBLIC

    @Test
    fun create() = runTest {

        val ctx = WrkContext(
            command = WrkCommand.CREATE,
            state = WrkState.NONE,
            workMode = WrkWorkMode.STUB,
            stubCase = WrkStubs.SUCCESS,
            tngRequest = WrkTng(
                id = id,
                title = title,
                description = description,
                tngType = tngType,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(WrkTngStub.get().id, ctx.tngResponse.id)
        assertEquals(title, ctx.tngResponse.title)
        assertEquals(description, ctx.tngResponse.description)
        assertEquals(tngType, ctx.tngResponse.tngType)
        assertEquals(visibility, ctx.tngResponse.visibility)
    }

    @Test
    fun badTitle() = runTest {
        val ctx = WrkContext(
            command = WrkCommand.CREATE,
            state = WrkState.NONE,
            workMode = WrkWorkMode.STUB,
            stubCase = WrkStubs.BAD_TITLE,
            tngRequest = WrkTng(
                id = id,
                title = "",
                description = description,
                tngType = tngType,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(WrkTng(), ctx.tngResponse)
        assertEquals("title", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
    @Test
    fun badDescription() = runTest {
        val ctx = WrkContext(
            command = WrkCommand.CREATE,
            state = WrkState.NONE,
            workMode = WrkWorkMode.STUB,
            stubCase = WrkStubs.BAD_DESCRIPTION,
            tngRequest = WrkTng(
                id = id,
                title = title,
                description = "",
                tngType = tngType,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(WrkTng(), ctx.tngResponse)
        assertEquals("description", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = WrkContext(
            command = WrkCommand.CREATE,
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
            command = WrkCommand.CREATE,
            state = WrkState.NONE,
            workMode = WrkWorkMode.STUB,
            stubCase = WrkStubs.BAD_ID,
            tngRequest = WrkTng(
                id = id,
                title = title,
                description = description,
                tngType = tngType,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(WrkTng(), ctx.tngResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}
