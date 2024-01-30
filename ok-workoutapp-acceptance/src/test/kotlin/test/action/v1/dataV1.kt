package com.github.hamlet_rt.workoutapp.blackbox.test.action.v1

import com.github.hamlet_rt.workoutapp.api.v1.models.*


val debug = TngDebug(mode = TngRequestDebugMode.STUB, stub = TngRequestDebugStubs.SUCCESS)

val someCreateTng = TngCreateObject(
    title = "Силовая тренировка",
    description = "Силовая тренировка на все тело",
    tngType = TngType.POWER,
    visibility = TngVisibility.PUBLIC
)
