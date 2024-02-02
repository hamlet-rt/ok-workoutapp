package com.github.hamlet_rt.workoutapp.repo.inmemory

import com.github.hamlet_rt.workoutapp.backend.repo.tests.RepoTngDeleteTest
import com.github.hamlet_rt.workoutapp.common.repo.ITngRepository

class TngRepoInMemoryDeleteTest : RepoTngDeleteTest() {
    override val repo: ITngRepository = TngRepoInMemory(
        initObjects = initObjects
    )
}
