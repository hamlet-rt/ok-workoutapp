package com.github.hamlet_rt.workoutapp.mappers.v1

import com.github.hamlet_rt.workoutapp.api.v1.models.*
import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.models.*
import com.github.hamlet_rt.workoutapp.mappers.v1.exceptions.UnknownWrkCommand

fun WrkContext.toTransportTng(): IResponse = when (val cmd = command) {
    WrkCommand.CREATE -> toTransportCreate()
    WrkCommand.READ -> toTransportRead()
    WrkCommand.UPDATE -> toTransportUpdate()
    WrkCommand.DELETE -> toTransportDelete()
    WrkCommand.SEARCH -> toTransportSearch()
    WrkCommand.OFFERS -> toTransportExercises()
    WrkCommand.NONE -> throw UnknownWrkCommand(cmd)
}

fun WrkContext.toTransportCreate() = TngCreateResponse(
    responseType = "create",
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == WrkState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    tng = tngResponse.toTransportTng()
)

fun WrkContext.toTransportRead() = TngReadResponse(
    responseType = "read",
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == WrkState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    tng = tngResponse.toTransportTng()
)

fun WrkContext.toTransportUpdate() = TngUpdateResponse(
    responseType = "update",
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == WrkState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    tng = tngResponse.toTransportTng()
)

fun WrkContext.toTransportDelete() = TngDeleteResponse(
    responseType = "delete",
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == WrkState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    tng = tngResponse.toTransportTng()
)

fun WrkContext.toTransportSearch() = TngSearchResponse(
    responseType = "search",
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == WrkState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    tngs = tngsResponse.toTransportTng()
)

fun WrkContext.toTransportExercises() = TngExercisesResponse(
    responseType = "offers",
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == WrkState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    tngs = tngsResponse.toTransportTng()
)

fun List<WrkTng>.toTransportTng(): List<TngResponseObject>? = this
    .map { it.toTransportTng() }
    .toList()
    .takeIf { it.isNotEmpty() }

fun WrkContext.toTransportInit() = TngInitResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (errors.isEmpty()) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
)

private fun WrkTng.toTransportTng(): TngResponseObject = TngResponseObject(
    id = id.takeIf { it != WrkTngId.NONE }?.asString(),
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    ownerId = ownerId.takeIf { it != WrkUserId.NONE }?.asString(),
    tngType = tngType.toTransportTng(),
    visibility = visibility.toTransportTng(),
    permissions = permissionsClient.toTransportTng(),
)

private fun Set<WrkTngPermissionClient>.toTransportTng(): Set<TngPermissions>? = this
    .map { it.toTransportTng() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun WrkTngPermissionClient.toTransportTng() = when (this) {
    WrkTngPermissionClient.READ -> TngPermissions.READ
    WrkTngPermissionClient.UPDATE -> TngPermissions.UPDATE
    WrkTngPermissionClient.MAKE_VISIBLE_OWNER -> TngPermissions.MAKE_VISIBLE_OWN
    WrkTngPermissionClient.MAKE_VISIBLE_GROUP -> TngPermissions.MAKE_VISIBLE_GROUP
    WrkTngPermissionClient.MAKE_VISIBLE_PUBLIC -> TngPermissions.MAKE_VISIBLE_PUBLIC
    WrkTngPermissionClient.DELETE -> TngPermissions.DELETE
}

private fun WrkVisibility.toTransportTng(): TngVisibility? = when (this) {
    WrkVisibility.VISIBLE_PUBLIC -> TngVisibility.PUBLIC
    WrkVisibility.VISIBLE_TO_GROUP -> TngVisibility.REGISTERED_ONLY
    WrkVisibility.VISIBLE_TO_OWNER -> TngVisibility.OWNER_ONLY
    WrkVisibility.NONE -> null
}

private fun WrkTngType.toTransportTng(): TngType? = when (this) {
    WrkTngType.POWER -> TngType.POWER
    WrkTngType.CARDIO -> TngType.CARDIO
    WrkTngType.FUNCTIONAL -> TngType.FUNCTIONAL
    WrkTngType.NONE -> null
}

private fun List<WrkError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportTng() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun WrkError.toTransportTng() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)
