package com.github.hamlet_rt.workoutapp.biz.repo

import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.models.WrkState
import com.github.hamlet_rt.workoutapp.common.repo.DbTngIdRequest
import com.github.hamlet_rt.workoutapp.cor.ICorChainDsl
import com.github.hamlet_rt.workoutapp.cor.worker

fun ICorChainDsl<WrkContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Чтение объявления из БД"
    on { state == WrkState.RUNNING }
    handle {
        val request = DbTngIdRequest(tngValidated)
        val result = tngRepo.readTng(request)
        val resultTng = result.data
        if (result.isSuccess && resultTng != null) {
            tngRepoRead = resultTng
        } else {
            state = WrkState.FAILING
            errors.addAll(result.errors)
        }
    }
}
