package com.github.hamlet_rt.workoutapp.biz.stub

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import com.github.hamlet_rt.workoutapp.biz.WrkTngProcessor
import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.models.*
import com.github.hamlet_rt.workoutapp.common.stubs.WrkStubs
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class TngUpdateStubTest {

    private val processor = WrkTngProcessor()
    val id = WrkTngId("777")
    val title = "title 666"
    val description = "desc 666"
    val tngType = WrkTngType.CARDIO
    val visibility = WrkVisibility.VISIBLE_PUBLIC

    @Test
    fun create() = runTest {

        val ctx = WrkContext(
            command = WrkCommand.UPDATE,
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
        assertEquals(id, ctx.tngResponse.id)
        assertEquals(title, ctx.tngResponse.title)
        assertEquals(description, ctx.tngResponse.description)
        assertEquals(tngType, ctx.tngResponse.tngType)
        assertEquals(visibility, ctx.tngResponse.visibility)
    }

    @Test
    fun badId() = runTest {
        val ctx = WrkContext(
            command = WrkCommand.UPDATE,
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
    fun badTitle() = runTest {
        val ctx = WrkContext(
            command = WrkCommand.UPDATE,
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
            command = WrkCommand.UPDATE,
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
            command = WrkCommand.UPDATE,
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
            command = WrkCommand.UPDATE,
            state = WrkState.NONE,
            workMode = WrkWorkMode.STUB,
            stubCase = WrkStubs.BAD_SEARCH_STRING,
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
