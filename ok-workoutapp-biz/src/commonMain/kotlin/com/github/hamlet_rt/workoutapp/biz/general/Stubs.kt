package com.github.hamlet_rt.workoutapp.biz.general

import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.models.WrkState
import com.github.hamlet_rt.workoutapp.common.models.WrkWorkMode
import com.github.hamlet_rt.workoutapp.cor.ICorChainDsl
import com.github.hamlet_rt.workoutapp.cor.chain

fun ICorChainDsl<WrkContext>.stubs(title: String, block: ICorChainDsl<WrkContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == WrkWorkMode.STUB && state == WrkState.RUNNING }
}
