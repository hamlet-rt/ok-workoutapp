package com.github.hamlet_rt.workoutapp.backend.repo.tests

import com.github.hamlet_rt.workoutapp.common.models.*
import com.github.hamlet_rt.workoutapp.common.repo.DbTngRequest
import com.github.hamlet_rt.workoutapp.common.repo.ITngRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoTngCreateTest {
    abstract val repo: ITngRepository

    protected open val lockNew: WrkTngLock = WrkTngLock("20000000-0000-0000-0000-000000000002")

    private val createObj = WrkTng(
        title = "create object",
        description = "create object description",
        ownerId = WrkUserId("owner-123"),
        visibility = WrkVisibility.VISIBLE_TO_GROUP,
        tngType = WrkTngType.CARDIO,
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createTng(DbTngRequest(createObj))
        val expected = createObj.copy(id = result.data?.id ?: WrkTngId.NONE)
        assertEquals(true, result.isSuccess)
        assertEquals(expected.title, result.data?.title)
        assertEquals(expected.description, result.data?.description)
        assertEquals(expected.tngType, result.data?.tngType)
        assertNotEquals(WrkTngId.NONE, result.data?.id)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockNew, result.data?.lock)
    }

    companion object : BaseInitTngs("create") {
        override val initObjects: List<WrkTng> = emptyList()
    }
}
