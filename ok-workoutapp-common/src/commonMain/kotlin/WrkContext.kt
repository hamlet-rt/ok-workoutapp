package com.github.hamlet_rt.workoutapp.common

import kotlinx.datetime.Instant
import com.github.hamlet_rt.workoutapp.common.models.*
import com.github.hamlet_rt.workoutapp.common.repo.ITngRepository
import com.github.hamlet_rt.workoutapp.common.stubs.WrkStubs


data class WrkContext(
    var command: WrkCommand = WrkCommand.NONE,
    var state: WrkState = WrkState.NONE,
    val errors: MutableList<WrkError> = mutableListOf(),
    var settings: WrkCorSettings = WrkCorSettings.NONE,

    var workMode: WrkWorkMode = WrkWorkMode.PROD,
    var stubCase: WrkStubs = WrkStubs.NONE,

    var tngRepo: ITngRepository = ITngRepository.NONE,
    var tngRepoRead: WrkTng = WrkTng(),
    var tngRepoPrepare: WrkTng = WrkTng(),
    var tngRepoDone: WrkTng = WrkTng(),
    var tngsRepoDone: MutableList<WrkTng> = mutableListOf(),

    var requestId: WrkRequestId = WrkRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var tngRequest: WrkTng = WrkTng(),
    var tngFilterRequest: WrkTngFilter = WrkTngFilter(),

    var tngValidating: WrkTng = WrkTng(),
    var tngFilterValidating: WrkTngFilter = WrkTngFilter(),

    var tngValidated: WrkTng = WrkTng(),
    var tngFilterValidated: WrkTngFilter = WrkTngFilter(),

    var tngResponse: WrkTng = WrkTng(),
    var tngsResponse: MutableList<WrkTng> = mutableListOf(),
)
