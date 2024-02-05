package com.github.hamlet_rt.workoutapp.common.helpers

import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.exceptions.RepoConcurrencyException
import com.github.hamlet_rt.workoutapp.common.models.WrkError
import com.github.hamlet_rt.workoutapp.common.models.WrkState
import com.github.hamlet_rt.workoutapp.common.models.WrkTngLock

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
    exception: Exception? = null,
    level: WrkError.Level = WrkError.Level.ERROR,
) = WrkError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)

fun errorAdministration(
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    field: String = "",
    violationCode: String,
    description: String,
    level: WrkError.Level = WrkError.Level.ERROR,
) = WrkError(
    field = field,
    code = "administration-$violationCode",
    group = "administration",
    message = "Microservice management error: $description",
    level = level,
)

fun errorRepoConcurrency(
    expectedLock: WrkTngLock,
    actualLock: WrkTngLock?,
    exception: Exception? = null,
) = WrkError(
    field = "lock",
    code = "concurrency",
    group = "repo",
    message = "The object has been changed concurrently by another user or process",
    exception = exception ?: RepoConcurrencyException(expectedLock, actualLock),
)

val errorNotFound = WrkError(
    field = "id",
    message = "Not Found",
    code = "not-found"
)

val errorEmptyId = WrkError(
    field = "id",
    message = "Id must not be null or blank"
)