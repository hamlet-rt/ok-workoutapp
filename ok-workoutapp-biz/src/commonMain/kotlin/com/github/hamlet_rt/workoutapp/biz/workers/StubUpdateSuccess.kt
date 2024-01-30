package com.github.hamlet_rt.workoutapp.biz.workers

import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.models.WrkState
import com.github.hamlet_rt.workoutapp.common.models.WrkTngId
import com.github.hamlet_rt.workoutapp.common.models.WrkTngType
import com.github.hamlet_rt.workoutapp.common.models.WrkVisibility
import com.github.hamlet_rt.workoutapp.common.stubs.WrkStubs
import com.github.hamlet_rt.workoutapp.cor.ICorChainDsl
import com.github.hamlet_rt.workoutapp.cor.worker
import com.github.hamlet_rt.workoutapp.stubs.WrkTngStub

fun ICorChainDsl<WrkContext>.stubUpdateSuccess(title: String) = worker {
    this.title = title
    on { stubCase == WrkStubs.SUCCESS && state == WrkState.RUNNING }
    handle {
        state = WrkState.FINISHING
        val stub = WrkTngStub.prepareResult {
            tngRequest.id.takeIf { it != WrkTngId.NONE }?.also { this.id = it }
            tngRequest.title.takeIf { it.isNotBlank() }?.also { this.title = it }
            tngRequest.description.takeIf { it.isNotBlank() }?.also { this.description = it }
            tngRequest.tngType.takeIf { it != WrkTngType.NONE }?.also { this.tngType = it }
            tngRequest.visibility.takeIf { it != WrkVisibility.NONE }?.also { this.visibility = it }
        }
        tngResponse = stub
    }
}
