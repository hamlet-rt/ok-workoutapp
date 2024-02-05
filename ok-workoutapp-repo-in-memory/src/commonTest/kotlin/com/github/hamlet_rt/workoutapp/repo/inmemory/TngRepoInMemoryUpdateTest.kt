package com.github.hamlet_rt.workoutapp.repo.inmemory

import com.github.hamlet_rt.workoutapp.backend.repo.tests.RepoTngUpdateTest
import com.github.hamlet_rt.workoutapp.common.repo.ITngRepository

class TngRepoInMemoryUpdateTest : RepoTngUpdateTest() {
    override val repo: ITngRepository = TngRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}
