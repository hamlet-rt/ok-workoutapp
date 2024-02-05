package com.github.hamlet_rt.workoutapp.biz.general

import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.models.WrkState
import com.github.hamlet_rt.workoutapp.common.models.WrkWorkMode
import com.github.hamlet_rt.workoutapp.cor.ICorChainDsl
import com.github.hamlet_rt.workoutapp.cor.worker

fun ICorChainDsl<WrkContext>.prepareResult(title: String) = worker {
    this.title = title
    description = "Подготовка данных для ответа клиенту на запрос"
    on { workMode != WrkWorkMode.STUB }
    handle {
        tngResponse = tngRepoDone
        tngsResponse = tngsRepoDone
        state = when (val st = state) {
            WrkState.RUNNING -> WrkState.FINISHING
            else -> st
        }
    }
}
