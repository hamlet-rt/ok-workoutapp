package repo

import com.github.hamlet_rt.workoutapp.backend.repo.tests.TngRepositoryMock
import com.github.hamlet_rt.workoutapp.biz.WrkTngProcessor
import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.WrkCorSettings
import com.github.hamlet_rt.workoutapp.common.models.*
import com.github.hamlet_rt.workoutapp.common.repo.DbTngResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class BizRepoDeleteTest {

    private val userId = WrkUserId("321")
    private val command = WrkCommand.DELETE
    private val initTng = WrkTng(
        id = WrkTngId("123"),
        title = "abc",
        description = "abc",
        ownerId = userId,
        tngType = WrkTngType.POWER,
        visibility = WrkVisibility.VISIBLE_PUBLIC,
        lock = WrkTngLock("123-234-abc-ABC"),
    )
    private val repo by lazy {
        TngRepositoryMock(
            invokeReadTng = {
               DbTngResponse(
                   isSuccess = true,
                   data = initTng,
               )
            },
            invokeDeleteTng = {
                if (it.id == initTng.id)
                    DbTngResponse(
                        isSuccess = true,
                        data = initTng
                    )
                else DbTngResponse(isSuccess = false, data = null)
            }
        )
    }
    private val settings by lazy {
        WrkCorSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { WrkTngProcessor(settings) }

    @Test
    fun repoDeleteSuccessTest() = runTest {
        val tngToUpdate = WrkTng(
            id = WrkTngId("123"),
        )
        val ctx = WrkContext(
            command = command,
            state = WrkState.NONE,
            workMode = WrkWorkMode.TEST,
            tngRequest = tngToUpdate,
        )
        processor.exec(ctx)
        assertEquals(WrkState.FINISHING, ctx.state)
        assertTrue { ctx.errors.isEmpty() }
        assertEquals(initTng.id, ctx.tngResponse.id)
        assertEquals(initTng.title, ctx.tngResponse.title)
        assertEquals(initTng.description, ctx.tngResponse.description)
        assertEquals(initTng.tngType, ctx.tngResponse.tngType)
        assertEquals(initTng.visibility, ctx.tngResponse.visibility)
    }

    @Test
    fun repoDeleteNotFoundTest() = repoNotFoundTest(command)
}
