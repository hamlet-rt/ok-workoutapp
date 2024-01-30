package com.github.hamlet_rt.workoutapp.blackbox.docker

import com.github.hamlet_rt.workoutapp.blackbox.fixture.docker.AbstractDockerCompose


object SpringDockerCompose : AbstractDockerCompose(
    "app-spring_1", 8080, "spring/docker-compose-spring.yml"
)