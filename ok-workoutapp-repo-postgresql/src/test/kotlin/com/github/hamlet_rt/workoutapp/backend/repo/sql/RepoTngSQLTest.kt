package com.github.hamlet_rt.workoutapp.backend.repo.sql

import com.github.hamlet_rt.workoutapp.backend.repo.tests.*
import com.github.hamlet_rt.workoutapp.common.repo.ITngRepository

class RepoTngSQLCreateTest : RepoTngCreateTest() {
    override val repo: ITngRepository = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}

class RepoAdSQLDeleteTest : RepoTngDeleteTest() {
    override val repo: ITngRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoAdSQLReadTest : RepoTngReadTest() {
    override val repo: ITngRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoAdSQLSearchTest : RepoTngSearchTest() {
    override val repo: ITngRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoAdSQLUpdateTest : RepoTngUpdateTest() {
    override val repo: ITngRepository = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}
