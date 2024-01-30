package com.github.hamlet_rt.workoutapp.biz.validation

import com.github.hamlet_rt.workoutapp.biz.WrkTngProcessor
import com.github.hamlet_rt.workoutapp.common.models.WrkCommand
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationUpdateTest {

    private val command = WrkCommand.UPDATE
    private val processor by lazy { WrkTngProcessor() }

    @Test fun correctTitle() = validationTitleCorrect(command, processor)
    @Test fun trimTitle() = validationTitleTrim(command, processor)
    @Test fun emptyTitle() = validationTitleEmpty(command, processor)
    @Test fun badSymbolsTitle() = validationTitleSymbols(command, processor)

    @Test fun correctDescription() = validationDescriptionCorrect(command, processor)
    @Test fun trimDescription() = validationDescriptionTrim(command, processor)
    @Test fun emptyDescription() = validationDescriptionEmpty(command, processor)
    @Test fun badSymbolsDescription() = validationDescriptionSymbols(command, processor)

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)


}

