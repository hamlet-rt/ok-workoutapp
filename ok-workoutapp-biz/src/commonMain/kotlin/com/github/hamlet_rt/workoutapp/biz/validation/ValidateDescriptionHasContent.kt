package com.github.hamlet_rt.workoutapp.biz.validation

import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.helpers.errorValidation
import com.github.hamlet_rt.workoutapp.common.helpers.fail
import com.github.hamlet_rt.workoutapp.cor.ICorChainDsl
import com.github.hamlet_rt.workoutapp.cor.worker

// TODO-validation-7: пример обработки ошибки в рамках бизнес-цепочки
fun ICorChainDsl<WrkContext>.validateDescriptionHasContent(title: String) = worker {
    this.title = title
    val regExp = Regex("\\p{L}")
    on { tngValidating.description.isNotEmpty() && !tngValidating.description.contains(regExp) }
    handle {
        fail(
            errorValidation(
                field = "description",
                violationCode = "noContent",
                description = "field must contain letters"
            )
        )
    }
}
