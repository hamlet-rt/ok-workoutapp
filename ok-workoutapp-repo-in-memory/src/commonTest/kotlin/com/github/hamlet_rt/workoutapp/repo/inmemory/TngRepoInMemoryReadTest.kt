package com.github.hamlet_rt.workoutapp.repo.inmemory

import com.github.hamlet_rt.workoutapp.backend.repo.tests.RepoTngReadTest
import com.github.hamlet_rt.workoutapp.common.repo.ITngRepository

class TngRepoInMemoryReadTest: RepoTngReadTest() {
    override val repo: ITngRepository = TngRepoInMemory(
        initObjects = initObjects
    )
}
