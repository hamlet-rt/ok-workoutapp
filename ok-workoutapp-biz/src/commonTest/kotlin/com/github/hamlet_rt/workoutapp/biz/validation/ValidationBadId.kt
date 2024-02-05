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

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdCorrect(command: WrkCommand, processor: WrkTngProcessor) = runTest {
    val ctx = WrkContext(
        command = command,
        state = WrkState.NONE,
        workMode = WrkWorkMode.TEST,
        tngRequest = WrkTngStub.prepareResult {
            lock = WrkTngLock("123-234-abc-ABC")
        }
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(WrkState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdTrim(command: WrkCommand, processor: WrkTngProcessor) = runTest {
    val tngRequest = WrkTngStub.get()
    tngRequest.id = WrkTngId(" \n\t 666 \n\t ")
    val ctx = WrkContext(
        command = command,
        state = WrkState.NONE,
        workMode = WrkWorkMode.TEST,
        tngRequest = WrkTng(
            id = WrkTngId(" \n\t 123-234-abc-ABC \n\t "),
            title = "abc",
            description = "abc",
            tngType = WrkTngType.POWER,
            visibility = WrkVisibility.VISIBLE_PUBLIC,
            lock = WrkTngLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(WrkState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdEmpty(command: WrkCommand, processor: WrkTngProcessor) = runTest {
    val tngRequest = WrkTngStub.get()
    tngRequest.id = WrkTngId("")
    val ctx = WrkContext(
        command = command,
        state = WrkState.NONE,
        workMode = WrkWorkMode.TEST,
        tngRequest = WrkTng(
            id = WrkTngId(""),
            title = "abc",
            description = "abc",
            tngType = WrkTngType.POWER,
            visibility = WrkVisibility.VISIBLE_PUBLIC,
            lock = WrkTngLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(WrkState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdFormat(command: WrkCommand, processor: WrkTngProcessor) = runTest {
    val adRequest = WrkTngStub.get()
    adRequest.id = WrkTngId("!@#\$%^&*(),.{}")
    val ctx = WrkContext(
        command = command,
        state = WrkState.NONE,
        workMode = WrkWorkMode.TEST,
        tngRequest = WrkTng(
            id = WrkTngId("!@#\$%^&*(),.{}"),
            title = "abc",
            description = "abc",
            tngType = WrkTngType.POWER,
            visibility = WrkVisibility.VISIBLE_PUBLIC,
            lock = WrkTngLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(WrkState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}
