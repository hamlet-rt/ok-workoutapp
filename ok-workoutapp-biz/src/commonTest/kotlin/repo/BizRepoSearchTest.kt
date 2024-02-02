package repo

import com.github.hamlet_rt.workoutapp.backend.repo.tests.TngRepositoryMock
import com.github.hamlet_rt.workoutapp.biz.WrkTngProcessor
import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.WrkCorSettings
import com.github.hamlet_rt.workoutapp.common.models.*
import com.github.hamlet_rt.workoutapp.common.repo.DbTngsResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BizRepoSearchTest {

    private val userId = WrkUserId("321")
    private val command = WrkCommand.SEARCH
    private val initTng = WrkTng(
        id = WrkTngId("123"),
        title = "abc",
        description = "abc",
        ownerId = userId,
        tngType = WrkTngType.POWER,
        visibility = WrkVisibility.VISIBLE_PUBLIC,
    )
    private val repo by lazy { TngRepositoryMock(
        invokeSearchTng = {
            DbTngsResponse(
                isSuccess = true,
                data = listOf(initTng),
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
    fun repoSearchSuccessTest() = runTest {
        val ctx = WrkContext(
            command = command,
            state = WrkState.NONE,
            workMode = WrkWorkMode.TEST,
            tngFilterRequest = WrkTngFilter(
                searchString = "ab",
                wrkTngType = WrkTngType.POWER,
            ),
        )
        processor.exec(ctx)
        assertEquals(WrkState.FINISHING, ctx.state)
        assertEquals(1, ctx.tngsResponse.size)
    }
}
