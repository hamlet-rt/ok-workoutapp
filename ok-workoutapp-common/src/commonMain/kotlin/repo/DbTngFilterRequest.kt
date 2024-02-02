package com.github.hamlet_rt.workoutapp.common.repo

import com.github.hamlet_rt.workoutapp.common.models.WrkTngType
import com.github.hamlet_rt.workoutapp.common.models.WrkUserId

data class DbTngFilterRequest(
    val titleFilter: String = "",
    val ownerId: WrkUserId = WrkUserId.NONE,
    val wrkTngType: WrkTngType = WrkTngType.NONE,
)
