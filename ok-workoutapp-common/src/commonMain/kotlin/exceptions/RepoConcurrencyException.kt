package com.github.hamlet_rt.workoutapp.common.exceptions

import com.github.hamlet_rt.workoutapp.common.models.WrkTngLock

class RepoConcurrencyException(expectedLock: WrkTngLock, actualLock: WrkTngLock?): RuntimeException(
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
)
