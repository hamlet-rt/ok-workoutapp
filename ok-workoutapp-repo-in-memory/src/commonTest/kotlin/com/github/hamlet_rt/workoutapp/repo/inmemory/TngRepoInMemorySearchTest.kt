package com.github.hamlet_rt.workoutapp.repo.inmemory

import com.github.hamlet_rt.workoutapp.backend.repo.tests.RepoTngSearchTest
import com.github.hamlet_rt.workoutapp.common.repo.ITngRepository

class TngRepoInMemorySearchTest : RepoTngSearchTest() {
    override val repo: ITngRepository = TngRepoInMemory(
        initObjects = initObjects
    )
}
