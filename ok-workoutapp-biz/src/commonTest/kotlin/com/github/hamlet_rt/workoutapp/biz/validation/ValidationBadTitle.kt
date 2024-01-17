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
fun validationTitleCorrect(command: WrkCommand, processor: WrkTngProcessor) = runTest {
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
    assertEquals("abc", ctx.tngValidated.title)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTitleTrim(command: WrkCommand, processor: WrkTngProcessor) = runTest {
    val ctx = WrkContext(
        command = command,
        state = WrkState.NONE,
        workMode = WrkWorkMode.TEST,
        tngRequest = WrkTng(
            id = stub.id,
            title = " \n\t abc \t\n ",
            description = "abc",
            tngType = WrkTngType.POWER,
            visibility = WrkVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(WrkState.FAILING, ctx.state)
    assertEquals("abc", ctx.tngValidated.title)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTitleEmpty(command: WrkCommand, processor: WrkTngProcessor) = runTest {
    val ctx = WrkContext(
        command = command,
        state = WrkState.NONE,
        workMode = WrkWorkMode.TEST,
        tngRequest = WrkTng(
            id = stub.id,
            title = "",
            description = "abc",
            tngType = WrkTngType.POWER,
            visibility = WrkVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(WrkState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("title", error?.field)
    assertContains(error?.message ?: "", "title")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTitleSymbols(command: WrkCommand, processor: WrkTngProcessor) = runTest {
    val ctx = WrkContext(
        command = command,
        state = WrkState.NONE,
        workMode = WrkWorkMode.TEST,
        tngRequest = WrkTng(
            id = WrkTngId("123"),
            title = "!@#$%^&*(),.{}",
            description = "abc",
            tngType = WrkTngType.POWER,
            visibility = WrkVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(WrkState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("title", error?.field)
    assertContains(error?.message ?: "", "title")
}
