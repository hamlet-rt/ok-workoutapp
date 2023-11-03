package com.github.hamlet_rt.workoutapp.blackbox.test

import io.kotest.core.annotation.Ignored
import com.github.hamlet_rt.workoutapp.blackbox.docker.WiremockDockerCompose
import com.github.hamlet_rt.workoutapp.blackbox.fixture.BaseFunSpec
import com.github.hamlet_rt.workoutapp.blackbox.fixture.client.RestClient

@Ignored
open class AccRestTestBase(dockerCompose: WiremockDockerCompose) : BaseFunSpec(dockerCompose, {
    val client = RestClient(dockerCompose)

    testApiV1(client)
})
class AccRestWiremockTest : AccRestTestBase(WiremockDockerCompose)
// TODO class AccRestSpringTest : AccRestTestBase(SpringDockerCompose)
// TODO class AccRestKtorTest : AccRestTestBase(KtorDockerCompose)
