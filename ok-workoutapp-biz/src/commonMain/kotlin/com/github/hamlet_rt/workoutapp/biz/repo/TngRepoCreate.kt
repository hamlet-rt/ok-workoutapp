package com.github.hamlet_rt.workoutapp.biz.repo

import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.models.WrkState
import com.github.hamlet_rt.workoutapp.common.repo.DbTngRequest
import com.github.hamlet_rt.workoutapp.cor.ICorChainDsl
import com.github.hamlet_rt.workoutapp.cor.worker

fun ICorChainDsl<WrkContext>.repoCreate(title: String) = worker {
    this.title = title
    description = "Добавление объявления в БД"
    on { state == WrkState.RUNNING }
    handle {
        val request = DbTngRequest(tngRepoPrepare)
        val result = tngRepo.createTng(request)
        val resultAd = result.data
        if (result.isSuccess && resultAd != null) {
            tngRepoDone = resultAd
        } else {
            state = WrkState.FAILING
            errors.addAll(result.errors)
        }
    }
}
