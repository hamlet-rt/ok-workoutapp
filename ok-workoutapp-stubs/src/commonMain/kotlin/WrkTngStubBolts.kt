package com.github.hamlet_rt.workoutapp.stubs

import com.github.hamlet_rt.workoutapp.common.models.*

object WrkTngStubBolts {

    val TNG_POWER_FULL_BODY: WrkTng
        get() = WrkTng(
            id = WrkTngId("1"),
            title = "Силовая тренировка",
            description = "Силовая тренировка на все тело",
            ownerId = WrkUserId("user-1"),
            tngType = WrkTngType.POWER,
            visibility = WrkVisibility.VISIBLE_PUBLIC,
            permissionsClient = mutableSetOf(
                WrkTngPermissionClient.READ,
                WrkTngPermissionClient.UPDATE,
                WrkTngPermissionClient.DELETE,
                WrkTngPermissionClient.MAKE_VISIBLE_PUBLIC,
                WrkTngPermissionClient.MAKE_VISIBLE_GROUP,
                WrkTngPermissionClient.MAKE_VISIBLE_OWNER,
            )
        )
    val TNG_CARDIO = TNG_POWER_FULL_BODY.copy(tngType = WrkTngType.CARDIO)
    val TNG_FUNCTIONAL = TNG_POWER_FULL_BODY.copy(tngType = WrkTngType.FUNCTIONAL)
}
