package com.github.hamlet_rt.workoutapp.common.helpers

import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.models.WrkCommand

fun WrkContext.isUpdatableCommand() =
    this.command in listOf(WrkCommand.CREATE, WrkCommand.UPDATE, WrkCommand.DELETE)
