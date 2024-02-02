package com.github.hamlet_rt.workoutapp.common.models

data class WrkTng(
    var id: WrkTngId = WrkTngId.NONE,
    var title: String = "",
    var description: String = "",
    var ownerId: WrkUserId = WrkUserId.NONE,
    var tngType: WrkTngType = WrkTngType.NONE,
    var visibility: WrkVisibility = WrkVisibility.NONE,
    var exerciseId: WrkExerciseId = WrkExerciseId.NONE,
    var lock: WrkTngLock = WrkTngLock.NONE,
    val permissionsClient: MutableSet<WrkTngPermissionClient> = mutableSetOf()
) {
    fun deepCopy(): WrkTng = copy(
        permissionsClient = permissionsClient.toMutableSet(),
    )

    fun isEmpty() = this == NONE

    companion object {
        val NONE = WrkTng()
    }
}