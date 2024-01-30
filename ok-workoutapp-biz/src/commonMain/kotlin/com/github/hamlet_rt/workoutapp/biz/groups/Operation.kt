package com.github.hamlet_rt.workoutapp.biz.groups

import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.models.WrkCommand
import com.github.hamlet_rt.workoutapp.common.models.WrkState
import com.github.hamlet_rt.workoutapp.cor.ICorChainDsl
import com.github.hamlet_rt.workoutapp.cor.chain

fun ICorChainDsl<WrkContext>.operation(title: String, command: WrkCommand, block: ICorChainDsl<WrkContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { this.command == command && state == WrkState.RUNNING}
}

