package com.github.hamlet_rt.workoutapp.biz.repo

import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.models.WrkState
import com.github.hamlet_rt.workoutapp.common.repo.DbTngFilterRequest
import com.github.hamlet_rt.workoutapp.cor.ICorChainDsl
import com.github.hamlet_rt.workoutapp.cor.worker

fun ICorChainDsl<WrkContext>.repoSearch(title: String) = worker {
    this.title = title
    description = "Поиск объявлений в БД по фильтру"
    on { state == WrkState.RUNNING }
    handle {
        val request = DbTngFilterRequest(
            titleFilter = tngFilterValidated.searchString,
            ownerId = tngFilterValidated.ownerId,
            wrkTngType = tngFilterValidated.wrkTngType,
        )
        val result = tngRepo.searchTng(request)
        val resultAds = result.data
        if (result.isSuccess && resultAds != null) {
            tngsRepoDone = resultAds.toMutableList()
        } else {
            state = WrkState.FAILING
            errors.addAll(result.errors)
        }
    }
}
