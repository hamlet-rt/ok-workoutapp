package com.github.hamlet_rt.workoutapp.mappers.v1

import com.github.hamlet_rt.workoutapp.api.v1.models.*
import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.models.*
import com.github.hamlet_rt.workoutapp.common.stubs.WrkStubs
import com.github.hamlet_rt.workoutapp.mappers.v1.exceptions.UnknownRequestClass

fun WrkContext.fromTransport(request: IRequest) = when (request) {
    is TngCreateRequest -> fromTransport(request)
    is TngReadRequest -> fromTransport(request)
    is TngUpdateRequest -> fromTransport(request)
    is TngDeleteRequest -> fromTransport(request)
    is TngSearchRequest -> fromTransport(request)
    is TngExercisesRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun String?.toTngId() = this?.let { WrkTngId(it) } ?: WrkTngId.NONE
private fun String?.toTngWithId() = WrkTng(id = this.toTngId())
private fun String?.toTngLock() = this?.let { WrkTngLock(it) } ?: WrkTngLock.NONE
private fun IRequest?.requestId() = this?.requestId?.let { WrkRequestId(it) } ?: WrkRequestId.NONE

private fun TngDebug?.transportToWorkMode(): WrkWorkMode = when (this?.mode) {
    TngRequestDebugMode.PROD -> WrkWorkMode.PROD
    TngRequestDebugMode.TEST -> WrkWorkMode.TEST
    TngRequestDebugMode.STUB -> WrkWorkMode.STUB
    null -> WrkWorkMode.PROD
}

private fun TngDebug?.transportToStubCase(): WrkStubs = when (this?.stub) {
    TngRequestDebugStubs.SUCCESS -> WrkStubs.SUCCESS
    TngRequestDebugStubs.NOT_FOUND -> WrkStubs.NOT_FOUND
    TngRequestDebugStubs.BAD_ID -> WrkStubs.BAD_ID
    TngRequestDebugStubs.BAD_TITLE -> WrkStubs.BAD_TITLE
    TngRequestDebugStubs.BAD_DESCRIPTION -> WrkStubs.BAD_DESCRIPTION
    TngRequestDebugStubs.BAD_VISIBILITY -> WrkStubs.BAD_VISIBILITY
    TngRequestDebugStubs.CANNOT_DELETE -> WrkStubs.CANNOT_DELETE
    TngRequestDebugStubs.BAD_SEARCH_STRING -> WrkStubs.BAD_SEARCH_STRING
    null -> WrkStubs.NONE
}

fun WrkContext.fromTransport(request: TngCreateRequest) {
    command = WrkCommand.CREATE
    requestId = request.requestId()
    tngRequest = request.tng?.toInternal() ?: WrkTng()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun WrkContext.fromTransport(request: TngReadRequest) {
    command = WrkCommand.READ
    requestId = request.requestId()
    tngRequest = request.tng.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun TngReadObject?.toInternal(): WrkTng = if (this != null) {
    WrkTng(id = id.toTngId())
} else {
    WrkTng.NONE
}


fun WrkContext.fromTransport(request: TngUpdateRequest) {
    command = WrkCommand.UPDATE
    requestId = request.requestId()
    tngRequest = request.tng?.toInternal() ?: WrkTng()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun WrkContext.fromTransport(request: TngDeleteRequest) {
    command = WrkCommand.DELETE
    requestId = request.requestId()
    tngRequest = request.tng.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun TngDeleteObject?.toInternal(): WrkTng = if (this != null) {
    WrkTng(
        id = id.toTngId(),
        lock = lock.toTngLock(),
    )
} else {
    WrkTng.NONE
}

fun WrkContext.fromTransport(request: TngSearchRequest) {
    command = WrkCommand.SEARCH
    requestId = request.requestId()
    tngFilterRequest = request.tngFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun WrkContext.fromTransport(request: TngExercisesRequest) {
    command = WrkCommand.OFFERS
    requestId = request.requestId()
    tngRequest = request.tng?.id.toTngWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun TngSearchFilter?.toInternal(): WrkTngFilter = WrkTngFilter(
    searchString = this?.searchString ?: ""
)

private fun TngCreateObject.toInternal(): WrkTng = WrkTng(
    title = this.title ?: "",
    description = this.description ?: "",
    tngType = this.tngType.fromTransport(),
    visibility = this.visibility.fromTransport(),
)

private fun TngUpdateObject.toInternal(): WrkTng = WrkTng(
    id = this.id.toTngId(),
    title = this.title ?: "",
    description = this.description ?: "",
    tngType = this.tngType.fromTransport(),
    visibility = this.visibility.fromTransport(),
    lock = lock.toTngLock(),
)

private fun TngVisibility?.fromTransport(): WrkVisibility = when (this) {
    TngVisibility.PUBLIC -> WrkVisibility.VISIBLE_PUBLIC
    TngVisibility.OWNER_ONLY -> WrkVisibility.VISIBLE_TO_OWNER
    TngVisibility.REGISTERED_ONLY -> WrkVisibility.VISIBLE_TO_GROUP
    null -> WrkVisibility.NONE
}

private fun TngType?.fromTransport(): WrkTngType = when (this) {
    TngType.POWER -> WrkTngType.POWER
    TngType.CARDIO -> WrkTngType.CARDIO
    TngType.FUNCTIONAL -> WrkTngType.FUNCTIONAL
    null -> WrkTngType.NONE
}

