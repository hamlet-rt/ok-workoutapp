package repo

import com.github.hamlet_rt.workoutapp.backend.repo.tests.TngRepositoryMock
import com.github.hamlet_rt.workoutapp.biz.WrkTngProcessor
import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.WrkCorSettings
import com.github.hamlet_rt.workoutapp.common.models.*
import com.github.hamlet_rt.workoutapp.common.repo.DbTngResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.assertEquals

private val initTng = WrkTng(
    id = WrkTngId("123"),
    title = "abc",
    description = "abc",
    tngType = WrkTngType.POWER,
    visibility = WrkVisibility.VISIBLE_PUBLIC,
)
private val repo = TngRepositoryMock(
        invokeReadTng = {
            if (it.id == initTng.id) {
                DbTngResponse(
                    isSuccess = true,
                    data = initTng,
                )
            } else DbTngResponse(
                isSuccess = false,
                data = null,
                errors = listOf(WrkError(message = "Not found", field = "id"))
            )
        }
    )
private val settings by lazy {
    WrkCorSettings(
        repoTest = repo
    )
}
private val processor by lazy { WrkTngProcessor(settings) }

@OptIn(ExperimentalCoroutinesApi::class)
fun repoNotFoundTest(command: WrkCommand) = runTest {
    val ctx = WrkContext(
        command = command,
        state = WrkState.NONE,
        workMode = WrkWorkMode.TEST,
        tngRequest = WrkTng(
            id = WrkTngId("12345"),
            title = "xyz",
            description = "xyz",
            tngType = WrkTngType.POWER,
            visibility = WrkVisibility.VISIBLE_TO_GROUP,
            lock = WrkTngLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(WrkState.FAILING, ctx.state)
    assertEquals(WrkTng(), ctx.tngResponse)
    assertEquals(1, ctx.errors.size)
    assertEquals("id", ctx.errors.first().field)
}
