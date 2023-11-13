package com.github.hamlet_rt.workoutapp.blackbox.fixture

import com.github.hamlet_rt.workoutapp.blackbox.docker.WiremockDockerCompose
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.test.TestCase

/**
 * Базовая реализация тестов, которая выполняет запуск и останов контейнеров, а также очистку БД.
 * Основана на FunSpec
 */
abstract class BaseFunSpec(
    private val dockerCompose: WiremockDockerCompose,
    body: FunSpec.() -> Unit) : FunSpec(body) {
    override suspend fun afterSpec(spec: Spec) {
        dockerCompose.stop()
    }

    override suspend fun beforeSpec(spec: Spec) {
        dockerCompose.start()
    }

    override suspend fun beforeEach(testCase: TestCase) {
        dockerCompose.clearDb()
    }
}