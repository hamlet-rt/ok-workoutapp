package com.github.hamlet_rt.workoutapp.common.helpers

import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.models.WrkError
import com.github.hamlet_rt.workoutapp.common.models.WrkState

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

fun WrkContext.fail(error: WrkError) {
    addError(error)
    state = WrkState.FAILING
}

fun errorValidation(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String,
    level: WrkError.Level = WrkError.Level.ERROR,
) = WrkError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)
