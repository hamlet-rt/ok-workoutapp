package com.github.hamlet_rt.workoutapp.biz.workers

import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.models.WrkState
import com.github.hamlet_rt.workoutapp.cor.ICorChainDsl
import com.github.hamlet_rt.workoutapp.cor.worker


fun ICorChainDsl<WrkContext>.initStatus(title: String) = worker() {
    this.title = title
    on { state == WrkState.NONE }
    handle { state = WrkState.RUNNING }
}

