package com.github.hamlet_rt.workoutapp.springapp.api.v1.controller

import com.github.hamlet_rt.workoutapp.api.v1.models.TngExercisesRequest
import com.github.hamlet_rt.workoutapp.api.v1.models.TngExercisesResponse
import com.github.hamlet_rt.workoutapp.biz.WrkTngProcessor
import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.mappers.v1.fromTransport
import com.github.hamlet_rt.workoutapp.mappers.v1.toTransportExercises
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/tng")
class ExercisesController(private val processor: WrkTngProcessor) {

    @PostMapping("exercises")
    suspend fun searchExercises(@RequestBody request: TngExercisesRequest): TngExercisesResponse {
        val context = WrkContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportExercises()
    }
}
