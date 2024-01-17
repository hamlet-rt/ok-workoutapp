package com.github.hamlet_rt.workoutapp.biz.validation

import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.helpers.errorValidation
import com.github.hamlet_rt.workoutapp.common.helpers.fail
import com.github.hamlet_rt.workoutapp.cor.ICorChainDsl
import com.github.hamlet_rt.workoutapp.cor.worker

// TODO-validation-4: смотрим пример COR DSL валидации
fun ICorChainDsl<WrkContext>.validateTitleNotEmpty(title: String) = worker {
    this.title = title
    on { tngValidating.title.isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "title",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}
