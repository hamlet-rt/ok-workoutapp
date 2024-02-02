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
import kotlin.test.assertNotEquals

class BizRepoCreateTest {

    private val userId = WrkUserId("321")
    private val command = WrkCommand.CREATE
    private val uuid = "10000000-0000-0000-0000-000000000001"
    private val repo = TngRepositoryMock(
        invokeCreateTng = {
            DbTngResponse(
                isSuccess = true,
                data = WrkTng(
                    id = WrkTngId(uuid),
                    title = it.tng.title,
                    description = it.tng.description,
                    ownerId = userId,
                    tngType = it.tng.tngType,
                    visibility = it.tng.visibility,
                )
            )
        }
    )
    private val settings = WrkCorSettings(
        repoTest = repo
    )
    private val processor = WrkTngProcessor(settings)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoCreateSuccessTest() = runTest {
        val ctx = WrkContext(
            command = command,
            state = WrkState.NONE,
            workMode = WrkWorkMode.TEST,
            tngRequest = WrkTng(
                title = "abc",
                description = "abc",
                tngType = WrkTngType.POWER,
                visibility = WrkVisibility.VISIBLE_PUBLIC,
            ),
        )
        processor.exec(ctx)
        assertEquals(WrkState.FINISHING, ctx.state)
        assertNotEquals(WrkTngId.NONE, ctx.tngResponse.id)
        assertEquals("abc", ctx.tngResponse.title)
        assertEquals("abc", ctx.tngResponse.description)
        assertEquals(WrkTngType.POWER, ctx.tngResponse.tngType)
        assertEquals(WrkVisibility.VISIBLE_PUBLIC, ctx.tngResponse.visibility)
    }
}
