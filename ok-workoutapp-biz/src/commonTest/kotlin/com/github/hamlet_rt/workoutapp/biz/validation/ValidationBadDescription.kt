package com.github.hamlet_rt.workoutapp.biz.validation

import com.github.hamlet_rt.workoutapp.biz.WrkTngProcessor
import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.models.*
import com.github.hamlet_rt.workoutapp.stubs.WrkTngStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

private val stub = WrkTngStub.get()

@OptIn(ExperimentalCoroutinesApi::class)
fun validationDescriptionCorrect(command: WrkCommand, processor: WrkTngProcessor) = runTest {
    val ctx = WrkContext(
        command = command,
        state = WrkState.NONE,
        workMode = WrkWorkMode.TEST,
        tngRequest = WrkTng(
            id = stub.id,
            title = "abc",
            description = "abc",
            tngType = WrkTngType.POWER,
            visibility = WrkVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(WrkState.FAILING, ctx.state)
    assertEquals("abc", ctx.tngValidated.description)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationDescriptionTrim(command: WrkCommand, processor: WrkTngProcessor) = runTest {
    val ctx = WrkContext(
        command = command,
        state = WrkState.NONE,
        workMode = WrkWorkMode.TEST,
        tngRequest = WrkTng(
            id = stub.id,
            title = "abc",
            description = " \n\tabc \n\t",
            tngType = WrkTngType.POWER,
            visibility = WrkVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(WrkState.FAILING, ctx.state)
    assertEquals("abc", ctx.tngValidated.description)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationDescriptionEmpty(command: WrkCommand, processor: WrkTngProcessor) = runTest {
    val ctx = WrkContext(
        command = command,
        state = WrkState.NONE,
        workMode = WrkWorkMode.TEST,
        tngRequest = WrkTng(
            id = stub.id,
            title = "abc",
            description = "",
            tngType = WrkTngType.POWER,
            visibility = WrkVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(WrkState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("description", error?.field)
    assertContains(error?.message ?: "", "description")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationDescriptionSymbols(command: WrkCommand, processor: WrkTngProcessor) = runTest {
    val ctx = WrkContext(
        command = command,
        state = WrkState.NONE,
        workMode = WrkWorkMode.TEST,
        tngRequest = WrkTng(
            id = stub.id,
            title = "abc",
            description = "!@#$%^&*(),.{}",
            tngType = WrkTngType.POWER,
            visibility = WrkVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(WrkState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("description", error?.field)
    assertContains(error?.message ?: "", "description")
}
