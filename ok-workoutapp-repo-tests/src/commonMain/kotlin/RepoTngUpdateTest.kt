package com.github.hamlet_rt.workoutapp.backend.repo.tests

import com.github.hamlet_rt.workoutapp.common.models.*
import com.github.hamlet_rt.workoutapp.common.repo.DbTngRequest
import com.github.hamlet_rt.workoutapp.common.repo.ITngRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoTngUpdateTest {
    abstract val repo: ITngRepository
    protected open val updateSucc = initObjects[0]
    protected open val updateConc = initObjects[1]
    protected val updateIdNotFound = WrkTngId("tng-repo-update-not-found")
    protected val lockBad = WrkTngLock("20000000-0000-0000-0000-000000000009")
    protected val lockNew = WrkTngLock("20000000-0000-0000-0000-000000000002")

    private val reqUpdateSucc by lazy {
        WrkTng(
            id = updateSucc.id,
            title = "update object",
            description = "update object description",
            ownerId = WrkUserId("owner-123"),
            visibility = WrkVisibility.VISIBLE_TO_GROUP,
            tngType = WrkTngType.CARDIO,
            lock = initObjects.first().lock,
        )
    }
    private val reqUpdateNotFound = WrkTng(
        id = updateIdNotFound,
        title = "update object not found",
        description = "update object not found description",
        ownerId = WrkUserId("owner-123"),
        visibility = WrkVisibility.VISIBLE_TO_GROUP,
        tngType = WrkTngType.CARDIO,
        lock = initObjects.first().lock,
    )
    private val reqUpdateConc by lazy {
        WrkTng(
            id = updateConc.id,
            title = "update object not found",
            description = "update object not found description",
            ownerId = WrkUserId("owner-123"),
            visibility = WrkVisibility.VISIBLE_TO_GROUP,
            tngType = WrkTngType.CARDIO,
            lock = lockBad,
        )
    }

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateTng(DbTngRequest(reqUpdateSucc))
        assertEquals(true, result.isSuccess)
        assertEquals(reqUpdateSucc.id, result.data?.id)
        assertEquals(reqUpdateSucc.title, result.data?.title)
        assertEquals(reqUpdateSucc.description, result.data?.description)
        assertEquals(reqUpdateSucc.tngType, result.data?.tngType)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateTng(DbTngRequest(reqUpdateNotFound))
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun updateConcurrencyError() = runRepoTest {
        val result = repo.updateTng(DbTngRequest(reqUpdateConc))
        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(updateConc, result.data)
    }


    companion object : BaseInitTngs("update") {
        override val initObjects: List<WrkTng> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}
