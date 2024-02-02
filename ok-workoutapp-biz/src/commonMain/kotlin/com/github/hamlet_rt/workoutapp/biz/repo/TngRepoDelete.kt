package com.github.hamlet_rt.workoutapp.biz.repo

import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.models.WrkState
import com.github.hamlet_rt.workoutapp.common.repo.DbTngIdRequest
import com.github.hamlet_rt.workoutapp.cor.ICorChainDsl
import com.github.hamlet_rt.workoutapp.cor.worker

fun ICorChainDsl<WrkContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "Удаление объявления из БД по ID"
    on { state == WrkState.RUNNING }
    handle {
        val request = DbTngIdRequest(tngRepoPrepare)
        val result = tngRepo.deleteTng(request)
        if (!result.isSuccess) {
            state = WrkState.FAILING
            errors.addAll(result.errors)
        }
        tngRepoDone = tngRepoRead
    }
}
