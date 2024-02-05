package com.github.hamlet_rt.workoutapp.common.repo

import com.github.hamlet_rt.workoutapp.common.models.WrkError

interface IDbResponse<T> {
    val data: T?
    val isSuccess: Boolean
    val errors: List<WrkError>
}
