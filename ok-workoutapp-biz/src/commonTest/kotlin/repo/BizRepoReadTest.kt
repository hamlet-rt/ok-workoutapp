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

class BizRepoReadTest {

    private val userId = WrkUserId("321")
    private val command = WrkCommand.READ
    private val initAd = WrkTng(
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
                data = initAd,
            )
        }
    ) }
    private val settings by lazy {
        WrkCorSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { WrkTngProcessor(settings) }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoReadSuccessTest() = runTest {
        val ctx = WrkContext(
            command = command,
            state = WrkState.NONE,
            workMode = WrkWorkMode.TEST,
            tngRequest = WrkTng(
                id = WrkTngId("123"),
            ),
        )
        processor.exec(ctx)
        assertEquals(WrkState.FINISHING, ctx.state)
        assertEquals(initAd.id, ctx.tngResponse.id)
        assertEquals(initAd.title, ctx.tngResponse.title)
        assertEquals(initAd.description, ctx.tngResponse.description)
        assertEquals(initAd.tngType, ctx.tngResponse.tngType)
        assertEquals(initAd.visibility, ctx.tngResponse.visibility)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoReadNotFoundTest() = repoNotFoundTest(command)
}
