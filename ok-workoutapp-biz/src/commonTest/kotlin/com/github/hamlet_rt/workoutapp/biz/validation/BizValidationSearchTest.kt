package com.github.hamlet_rt.workoutapp.biz.validation

import com.github.hamlet_rt.workoutapp.biz.WrkTngProcessor
import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.models.WrkCommand
import com.github.hamlet_rt.workoutapp.common.models.WrkState
import com.github.hamlet_rt.workoutapp.common.models.WrkTngFilter
import com.github.hamlet_rt.workoutapp.common.models.WrkWorkMode
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationSearchTest {

    private val command = WrkCommand.SEARCH
    private val processor by lazy { WrkTngProcessor() }

    @Test
    fun correctEmpty() = runTest {
        val ctx = WrkContext(
            command = command,
            state = WrkState.NONE,
            workMode = WrkWorkMode.TEST,
            tngFilterRequest = WrkTngFilter()
        )
        processor.exec(ctx)
        assertEquals(0, ctx.errors.size)
        assertNotEquals(WrkState.FAILING, ctx.state)
    }
}

