package com.github.hamlet_rt.workoutapp.biz.workers

import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.models.WrkState
import com.github.hamlet_rt.workoutapp.common.stubs.WrkStubs
import com.github.hamlet_rt.workoutapp.cor.ICorChainDsl
import com.github.hamlet_rt.workoutapp.cor.worker
import com.github.hamlet_rt.workoutapp.stubs.WrkTngStub

fun ICorChainDsl<WrkContext>.stubDeleteSuccess(title: String) = worker {
    this.title = title
    on { stubCase == WrkStubs.SUCCESS && state == WrkState.RUNNING }
    handle {
        state = WrkState.FINISHING
        val stub = WrkTngStub.prepareResult {
            tngRequest.title.takeIf { it.isNotBlank() }?.also { this.title = it }
        }
        tngResponse = stub
    }
}
