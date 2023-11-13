package com.github.hamlet_rt.workoutapp.common.helpers

import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.models.WrkError

fun Throwable.asWrkError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = WrkError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

fun WrkContext.addError(vararg error: WrkError) = errors.addAll(error)
