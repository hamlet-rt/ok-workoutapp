package com.github.hamlet_rt.workoutapp.backend.repo.tests

import com.github.hamlet_rt.workoutapp.common.models.WrkTng
import com.github.hamlet_rt.workoutapp.common.models.WrkTngType
import com.github.hamlet_rt.workoutapp.common.models.WrkUserId
import com.github.hamlet_rt.workoutapp.common.repo.DbTngFilterRequest
import com.github.hamlet_rt.workoutapp.common.repo.ITngRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoTngSearchTest {
    abstract val repo: ITngRepository

    protected open val initializedObjects: List<WrkTng> = initObjects

    @Test
    fun searchOwner() = runRepoTest {
        val result = repo.searchTng(DbTngFilterRequest(ownerId = searchOwnerId))
        assertEquals(true, result.isSuccess)
        val expected = listOf(initializedObjects[1], initializedObjects[3]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data?.sortedBy { it.id.asString() })
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun searchDealSide() = runRepoTest {
        val result = repo.searchTng(DbTngFilterRequest(wrkTngType = WrkTngType.CARDIO))
        assertEquals(true, result.isSuccess)
        val expected = listOf(initializedObjects[2], initializedObjects[4]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data?.sortedBy { it.id.asString() })
        assertEquals(emptyList(), result.errors)
    }

    companion object: BaseInitTngs("search") {

        val searchOwnerId = WrkUserId("owner-124")
        override val initObjects: List<WrkTng> = listOf(
            createInitTestModel("ad1"),
            createInitTestModel("ad2", ownerId = searchOwnerId),
            createInitTestModel("ad3", tngType = WrkTngType.CARDIO),
            createInitTestModel("ad4", ownerId = searchOwnerId),
            createInitTestModel("ad5", tngType = WrkTngType.CARDIO),
        )
    }
}
