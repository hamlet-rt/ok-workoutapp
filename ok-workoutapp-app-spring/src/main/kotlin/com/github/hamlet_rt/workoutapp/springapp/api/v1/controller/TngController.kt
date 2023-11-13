package com.github.hamlet_rt.workoutapp.springapp.api.v1.controller

import com.github.hamlet_rt.workoutapp.api.v1.models.*
import com.github.hamlet_rt.workoutapp.biz.WrkTngProcessor
import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.mappers.v1.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/tng")
class TngController(private val processor: WrkTngProcessor) {

    @PostMapping("create")
    suspend fun createTng(@RequestBody request: TngCreateRequest): TngCreateResponse {
        val context = WrkContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportCreate()
    }

    @PostMapping("read")
    suspend fun readTng(@RequestBody request: TngReadRequest): TngReadResponse {
        val context = WrkContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportRead()
    }

    @RequestMapping("update", method = [RequestMethod.POST])
    suspend fun updateTng(@RequestBody request: TngUpdateRequest): TngUpdateResponse {
        val context = WrkContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportUpdate()
    }

    @PostMapping("delete")
    suspend fun deleteTng(@RequestBody request: TngDeleteRequest): TngDeleteResponse {
        val context = WrkContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportDelete()
    }

    @PostMapping("search")
    suspend fun searchTng(@RequestBody request: TngSearchRequest): TngSearchResponse {
        val context = WrkContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportSearch()
    }
}
