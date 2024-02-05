package com.github.hamlet_rt.workoutapp.repo.inmemory

import com.github.hamlet_rt.workoutapp.backend.repo.tests.RepoTngCreateTest

class TngRepoInMemoryCreateTest : RepoTngCreateTest() {
    override val repo = TngRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}