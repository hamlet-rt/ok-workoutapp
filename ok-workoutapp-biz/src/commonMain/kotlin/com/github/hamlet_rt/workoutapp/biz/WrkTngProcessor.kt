package com.github.hamlet_rt.workoutapp.biz

import com.github.hamlet_rt.workoutapp.biz.groups.operation
import com.github.hamlet_rt.workoutapp.biz.groups.stubs
import com.github.hamlet_rt.workoutapp.biz.validation.*
import com.github.hamlet_rt.workoutapp.biz.workers.*
import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.models.WrkCommand
import com.github.hamlet_rt.workoutapp.common.models.WrkTngId
import com.github.hamlet_rt.workoutapp.cor.rootChain
import com.github.hamlet_rt.workoutapp.cor.worker

class WrkTngProcessor {

    suspend fun exec(ctx: WrkContext) = BusinessChain.exec(ctx)

    companion object {
        private val BusinessChain = rootChain<WrkContext> {
            initStatus("Инициализация статуса")

            operation("Создание тренировки", WrkCommand.CREATE) {
                stubs("Обработка стабов") {
                    stubCreateSuccess("Имитация успешной обработки")
                    stubValidationBadTitle("Имитация ошибки валидации заголовка")
                    stubValidationBadDescription("Имитация ошибки валидации описания")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adValidating") { tngValidating = tngRequest.deepCopy() }
                    worker("Очистка id") { tngValidating.id = WrkTngId.NONE }
                    worker("Очистка заголовка") { tngValidating.title = tngValidating.title.trim() }
                    worker("Очистка описания") { tngValidating.description = tngValidating.description.trim() }
                    validateTitleNotEmpty("Проверка, что заголовок не пуст")
                    validateTitleHasContent("Проверка символов")
                    validateDescriptionNotEmpty("Проверка, что описание не пусто")
                    validateDescriptionHasContent("Проверка символов")

                    finishAdValidation("Завершение проверок")
                }
            }
            operation("Получить тренировку", WrkCommand.READ) {
                stubs("Обработка стабов") {
                    stubReadSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adValidating") { tngValidating = tngRequest.deepCopy() }
                    worker("Очистка id") { tngValidating.id = WrkTngId(tngValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")

                    finishAdValidation("Успешное завершение процедуры валидации")
                }
            }
            operation("Изменить тренировку", WrkCommand.UPDATE) {
                stubs("Обработка стабов") {
                    stubUpdateSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubValidationBadTitle("Имитация ошибки валидации заголовка")
                    stubValidationBadDescription("Имитация ошибки валидации описания")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adValidating") { tngValidating = tngRequest.deepCopy() }
                    worker("Очистка id") { tngValidating.id = WrkTngId(tngValidating.id.asString().trim()) }
                    worker("Очистка заголовка") { tngValidating.title = tngValidating.title.trim() }
                    worker("Очистка описания") { tngValidating.description = tngValidating.description.trim() }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    validateTitleNotEmpty("Проверка на непустой заголовок")
                    validateTitleHasContent("Проверка на наличие содержания в заголовке")
                    validateDescriptionNotEmpty("Проверка на непустое описание")
                    validateDescriptionHasContent("Проверка на наличие содержания в описании")

                    finishAdValidation("Успешное завершение процедуры валидации")
                }
            }
            operation("Удалить тренировку", WrkCommand.DELETE) {
                stubs("Обработка стабов") {
                    stubDeleteSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adValidating") {
                        tngValidating = tngRequest.deepCopy() }
                    worker("Очистка id") { tngValidating.id = WrkTngId(tngValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    finishAdValidation("Успешное завершение процедуры валидации")
                }
            }
            operation("Поиск тренировок", WrkCommand.SEARCH) {
                stubs("Обработка стабов") {
                    stubSearchSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adFilterValidating") { tngFilterValidating = tngFilterRequest.copy() }

                    finishAdFilterValidation("Успешное завершение процедуры валидации")
                }
            }
        }.build()
    }
}
