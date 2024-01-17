package com.github.hamlet_rt.workoutapp.biz.validation

import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.helpers.errorValidation
import com.github.hamlet_rt.workoutapp.common.helpers.fail
import com.github.hamlet_rt.workoutapp.common.models.WrkTngId
import com.github.hamlet_rt.workoutapp.cor.ICorChainDsl
import com.github.hamlet_rt.workoutapp.cor.worker

fun ICorChainDsl<WrkContext>.validateIdProperFormat(title: String) = worker {
    this.title = title

    // Может быть вынесен в WrkTngId для реализации различных форматов
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { tngValidating.id != WrkTngId.NONE && !tngValidating.id.asString().matches(regExp) }
    handle {
        val encodedId = tngValidating.id.asString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(
            errorValidation(
                field = "id",
                violationCode = "badFormat",
                description = "value $encodedId must contain only letters and numbers"
            )
        )
    }
}
