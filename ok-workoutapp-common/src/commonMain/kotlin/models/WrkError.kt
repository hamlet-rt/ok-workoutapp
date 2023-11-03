package com.github.hamlet_rt.workoutapp.common.models

data class WrkError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val exception: Throwable? = null,
)
