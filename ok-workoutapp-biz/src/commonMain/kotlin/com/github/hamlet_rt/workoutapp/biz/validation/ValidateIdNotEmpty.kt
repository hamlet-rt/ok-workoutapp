package com.github.hamlet_rt.workoutapp.biz.validation

import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.helpers.errorValidation
import com.github.hamlet_rt.workoutapp.common.helpers.fail
import com.github.hamlet_rt.workoutapp.cor.ICorChainDsl
import com.github.hamlet_rt.workoutapp.cor.worker

fun ICorChainDsl<WrkContext>.validateIdNotEmpty(title: String) = worker {
    this.title = title
    on { tngValidating.id.asString().isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "id",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}
