package com.github.hamlet_rt.workoutapp.blackbox.test

import io.kotest.core.annotation.Ignored
import com.github.hamlet_rt.workoutapp.blackbox.docker.SpringDockerCompose
import com.github.hamlet_rt.workoutapp.blackbox.fixture.BaseFunSpec
import com.github.hamlet_rt.workoutapp.blackbox.fixture.client.RestClient
import com.github.hamlet_rt.workoutapp.blackbox.fixture.docker.DockerCompose

@Ignored
open class AccRestTestBase(dockerCompose: DockerCompose) : BaseFunSpec(dockerCompose, {
    val client = RestClient(dockerCompose)

    testApiV1(client, "rest ")
})
class AccRestSpringTest : AccRestTestBase(SpringDockerCompose)
// TODO class AccRestKtorTest : AccRestTestBase(KtorDockerCompose)
