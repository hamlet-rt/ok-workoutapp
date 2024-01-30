package com.github.hamlet_rt.workoutapp.biz.validation

import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.models.WrkState
import com.github.hamlet_rt.workoutapp.cor.ICorChainDsl
import com.github.hamlet_rt.workoutapp.cor.chain

fun ICorChainDsl<WrkContext>.validation(block: ICorChainDsl<WrkContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == WrkState.RUNNING }
}
