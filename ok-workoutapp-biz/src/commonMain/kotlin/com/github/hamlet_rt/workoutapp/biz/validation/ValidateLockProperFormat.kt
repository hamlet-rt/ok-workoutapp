package com.github.hamlet_rt.workoutapp.biz.validation

import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.helpers.errorValidation
import com.github.hamlet_rt.workoutapp.common.models.WrkTngLock
import com.github.hamlet_rt.workoutapp.cor.ICorChainDsl
import com.github.hamlet_rt.workoutapp.cor.worker
import com.github.hamlet_rt.workoutapp.common.helpers.fail

fun ICorChainDsl<WrkContext>.validateLockProperFormat(title: String) = worker {
    this.title = title

    // Может быть вынесен в WrkTngId для реализации различных форматов
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { tngValidating.lock != WrkTngLock.NONE && !tngValidating.lock.asString().matches(regExp) }
    handle {
        val encodedId = tngValidating.lock.asString()
        fail(
            errorValidation(
                field = "lock",
                violationCode = "badFormat",
                description = "value $encodedId must contain only"
            )
        )
    }
}
