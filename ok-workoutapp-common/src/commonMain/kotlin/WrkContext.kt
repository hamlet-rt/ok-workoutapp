package com.github.hamlet_rt.workoutapp.common

import kotlinx.datetime.Instant
import com.github.hamlet_rt.workoutapp.common.models.*
import com.github.hamlet_rt.workoutapp.common.stubs.WrkStubs


data class WrkContext(
    var command: WrkCommand = WrkCommand.NONE,
    var state: WrkState = WrkState.NONE,
    val errors: MutableList<WrkError> = mutableListOf(),

    var workMode: WrkWorkMode = WrkWorkMode.PROD,
    var stubCase: WrkStubs = WrkStubs.NONE,

    var requestId: WrkRequestId = WrkRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var tngRequest: WrkTng = WrkTng(),
    var tngFilterRequest: WrkTngFilter = WrkTngFilter(),
    var tngResponse: WrkTng = WrkTng(),
    var tngsResponse: MutableList<WrkTng> = mutableListOf(),
)
