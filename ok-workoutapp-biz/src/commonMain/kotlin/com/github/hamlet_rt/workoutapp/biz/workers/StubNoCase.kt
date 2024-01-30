package com.github.hamlet_rt.workoutapp.biz.workers

import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.helpers.fail
import com.github.hamlet_rt.workoutapp.common.models.WrkError
import com.github.hamlet_rt.workoutapp.common.models.WrkState
import com.github.hamlet_rt.workoutapp.cor.ICorChainDsl
import com.github.hamlet_rt.workoutapp.cor.worker

fun ICorChainDsl<WrkContext>.stubNoCase(title: String) = worker {
    this.title = title
    on { state == WrkState.RUNNING }
    handle {
        fail(
            WrkError(
                code = "validation",
                field = "stub",
                group = "validation",
                message = "Wrong stub case is requested: ${stubCase.name}"
            )
        )
    }
}
