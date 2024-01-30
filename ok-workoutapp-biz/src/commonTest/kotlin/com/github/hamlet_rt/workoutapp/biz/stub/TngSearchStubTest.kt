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
import kotlin.test.assertTrue
import kotlin.test.fail

@OptIn(ExperimentalCoroutinesApi::class)
class TngSearchStubTest {

    private val processor = WrkTngProcessor()
    val filter = WrkTngFilter(searchString = "Силовая тренировка p-01")

    @Test
    fun read() = runTest {

        val ctx = WrkContext(
            command = WrkCommand.SEARCH,
            state = WrkState.NONE,
            workMode = WrkWorkMode.STUB,
            stubCase = WrkStubs.SUCCESS,
            tngFilterRequest = filter,
        )
        processor.exec(ctx)
        assertTrue(ctx.tngsResponse.size > 1)
        val first = ctx.tngsResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.title.contains(filter.searchString))
        assertTrue(first.description.contains(filter.searchString))
        with (WrkTngStub.get()) {
            assertEquals(tngType, first.tngType)
            assertEquals(visibility, first.visibility)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = WrkContext(
            command = WrkCommand.SEARCH,
            state = WrkState.NONE,
            workMode = WrkWorkMode.STUB,
            stubCase = WrkStubs.BAD_ID,
            tngFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(WrkTng(), ctx.tngResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = WrkContext(
            command = WrkCommand.SEARCH,
            state = WrkState.NONE,
            workMode = WrkWorkMode.STUB,
            stubCase = WrkStubs.DB_ERROR,
            tngFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(WrkTng(), ctx.tngResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = WrkContext(
            command = WrkCommand.SEARCH,
            state = WrkState.NONE,
            workMode = WrkWorkMode.STUB,
            stubCase = WrkStubs.BAD_TITLE,
            tngFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(WrkTng(), ctx.tngResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
