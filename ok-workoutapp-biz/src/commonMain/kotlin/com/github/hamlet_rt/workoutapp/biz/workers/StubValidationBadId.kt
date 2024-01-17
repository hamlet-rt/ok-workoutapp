package com.github.hamlet_rt.workoutapp.biz.workers

import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.models.WrkError
import com.github.hamlet_rt.workoutapp.common.models.WrkState
import com.github.hamlet_rt.workoutapp.common.stubs.WrkStubs
import com.github.hamlet_rt.workoutapp.cor.ICorChainDsl
import com.github.hamlet_rt.workoutapp.cor.worker

fun ICorChainDsl<WrkContext>.stubValidationBadId(title: String) = worker {
    this.title = title
    on { stubCase == WrkStubs.BAD_ID && state == WrkState.RUNNING }
    handle {
        state = WrkState.FAILING
        this.errors.add(
            WrkError(
                group = "validation",
                code = "validation-id",
                field = "id",
                message = "Wrong id field"
            )
        )
    }
}
