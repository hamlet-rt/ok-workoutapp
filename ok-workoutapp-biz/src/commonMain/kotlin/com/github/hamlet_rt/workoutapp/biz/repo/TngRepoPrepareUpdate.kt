package com.github.hamlet_rt.workoutapp.biz.repo

import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.models.WrkState
import com.github.hamlet_rt.workoutapp.cor.ICorChainDsl
import com.github.hamlet_rt.workoutapp.cor.worker

fun ICorChainDsl<WrkContext>.repoPrepareUpdate(title: String) = worker {
    this.title = title
    description = "Готовим данные к сохранению в БД: совмещаем данные, прочитанные из БД, " +
            "и данные, полученные от пользователя"
    on { state == WrkState.RUNNING }
    handle {
        tngRepoPrepare = tngRepoRead.deepCopy().apply {
            this.title = tngValidated.title
            description = tngValidated.description
            tngType = tngValidated.tngType
            visibility = tngValidated.visibility
        }
    }
}
