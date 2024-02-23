package com.github.hamlet_rt.workoutapp.biz.validation

import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.models.WrkState
import com.github.hamlet_rt.workoutapp.cor.ICorChainDsl
import com.github.hamlet_rt.workoutapp.cor.worker

fun ICorChainDsl<WrkContext>.finishTngValidation(title: String) = worker {
    this.title = title
    on { state == WrkState.RUNNING }
    handle {
        tngValidated = tngValidating
    }
}

fun ICorChainDsl<WrkContext>.finishTngFilterValidation(title: String) = worker {
    this.title = title
    on { state == WrkState.RUNNING }
    handle {
        tngFilterValidated = tngFilterValidating
    }
}
