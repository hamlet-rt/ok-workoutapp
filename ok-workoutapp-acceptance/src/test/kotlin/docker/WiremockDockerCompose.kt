package com.github.hamlet_rt.workoutapp.blackbox.docker

import com.github.hamlet_rt.workoutapp.blackbox.fixture.docker.AbstractDockerCompose

object WiremockDockerCompose : AbstractDockerCompose(
    "app-wiremock_1", 8080, "wiremock/docker-compose-wiremock.yml"
)