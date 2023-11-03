package com.github.hamlet_rt.workoutapp.blackbox.test

import com.github.hamlet_rt.workoutapp.blackbox.fixture.client.Client
import io.kotest.core.spec.style.FunSpec
import com.github.hamlet_rt.workoutapp.blackbox.test.action.v1.createAd

fun FunSpec.testApiV1(client: Client) {
    context("v1") {
        test("Create Ad ok") {
            client.createAd()
        }
    }
}