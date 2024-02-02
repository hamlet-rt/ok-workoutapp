package com.github.hamlet_rt.workoutapp.common

import com.github.hamlet_rt.workoutapp.common.repo.ITngRepository

data class WrkCorSettings(
    val repoStub: ITngRepository = ITngRepository.NONE,
    val repoTest: ITngRepository = ITngRepository.NONE,
    val repoProd: ITngRepository = ITngRepository.NONE,
) {
    companion object {
        val NONE = WrkCorSettings()
    }
}
