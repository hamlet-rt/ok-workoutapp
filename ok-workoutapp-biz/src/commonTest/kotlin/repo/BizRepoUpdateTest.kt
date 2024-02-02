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

@OptIn(ExperimentalCoroutinesApi::class)
class BizRepoUpdateTest {

    private val userId = WrkUserId("321")
    private val command = WrkCommand.UPDATE
    private val initTng = WrkTng(
        id = WrkTngId("123"),
        title = "abc",
        description = "abc",
        ownerId = userId,
        tngType = WrkTngType.POWER,
        visibility = WrkVisibility.VISIBLE_PUBLIC,
    )
    private val repo by lazy { TngRepositoryMock(
        invokeReadTng = {
            DbTngResponse(
                isSuccess = true,
                data = initTng,
            )
        },
        invokeUpdateTng = {
            DbTngResponse(
                isSuccess = true,
                data = WrkTng(
                    id = WrkTngId("123"),
                    title = "xyz",
                    description = "xyz",
                    tngType = WrkTngType.POWER,
                    visibility = WrkVisibility.VISIBLE_TO_GROUP,
                )
            )
        }
    ) }
    private val settings by lazy {
        WrkCorSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { WrkTngProcessor(settings) }

    @Test
    fun repoUpdateSuccessTest() = runTest {
        val tngToUpdate = WrkTng(
            id = WrkTngId("123"),
            title = "xyz",
            description = "xyz",
            tngType = WrkTngType.POWER,
            visibility = WrkVisibility.VISIBLE_TO_GROUP,
            lock = WrkTngLock("123-234-abc-ABC"),
        )
        val ctx = WrkContext(
            command = command,
            state = WrkState.NONE,
            workMode = WrkWorkMode.TEST,
            tngRequest = tngToUpdate,
        )
        processor.exec(ctx)
        assertEquals(WrkState.FINISHING, ctx.state)
        assertEquals(tngToUpdate.id, ctx.tngResponse.id)
        assertEquals(tngToUpdate.title, ctx.tngResponse.title)
        assertEquals(tngToUpdate.description, ctx.tngResponse.description)
        assertEquals(tngToUpdate.tngType, ctx.tngResponse.tngType)
        assertEquals(tngToUpdate.visibility, ctx.tngResponse.visibility)
    }

    @Test
    fun repoUpdateNotFoundTest() = repoNotFoundTest(command)
}
