package com.github.hamlet_rt.workoutapp.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class WrkRequestId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = WrkRequestId("")
    }
}
