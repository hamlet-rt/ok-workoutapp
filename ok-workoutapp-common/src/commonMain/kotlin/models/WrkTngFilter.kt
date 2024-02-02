package com.github.hamlet_rt.workoutapp.common.models

data class WrkTngFilter(
    var searchString: String = "",
    var ownerId: WrkUserId = WrkUserId.NONE,
    var wrkTngType: WrkTngType = WrkTngType.NONE,
)
