package com.github.hamlet_rt.workoutapp.biz

import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.stubs.WrkTngStub

class WrkTngProcessor {
    suspend fun exec(ctx: WrkContext) {
        ctx.tngResponse = WrkTngStub.get()
    }
}
