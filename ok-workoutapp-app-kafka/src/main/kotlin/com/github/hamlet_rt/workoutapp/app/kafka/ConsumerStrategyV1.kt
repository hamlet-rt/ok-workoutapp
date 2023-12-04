package com.github.hamlet_rt.workoutapp.app.kafka

import apiV1RequestDeserialize
import apiV1ResponseSerialize
import com.github.hamlet_rt.workoutapp.api.v1.models.IRequest
import com.github.hamlet_rt.workoutapp.api.v1.models.IResponse
import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.mappers.v1.fromTransport
import com.github.hamlet_rt.workoutapp.mappers.v1.toTransportTng

class ConsumerStrategyV1 : ConsumerStrategy {
    override fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(config.kafkaTopicInV1, config.kafkaTopicOutV1)
    }

    override fun serialize(source: WrkContext): String {
        val response: IResponse = source.toTransportTng()
        return apiV1ResponseSerialize(response)
    }

    override fun deserialize(value: String, target: WrkContext) {
        val request: IRequest = apiV1RequestDeserialize(value)
        target.fromTransport(request)
    }
}