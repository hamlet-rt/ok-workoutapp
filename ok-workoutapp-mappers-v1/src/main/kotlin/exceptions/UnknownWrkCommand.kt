package com.github.hamlet_rt.workoutapp.mappers.v1.exceptions

import com.github.hamlet_rt.workoutapp.common.models.WrkCommand

class UnknownWrkCommand(command: WrkCommand) : Throwable("Wrong command $command at mapping toTransport stage")
