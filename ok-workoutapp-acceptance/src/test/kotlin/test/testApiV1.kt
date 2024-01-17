package com.github.hamlet_rt.workoutapp.blackbox.test

import com.github.hamlet_rt.workoutapp.api.v1.models.TngSearchFilter
import com.github.hamlet_rt.workoutapp.api.v1.models.TngUpdateObject
import com.github.hamlet_rt.workoutapp.blackbox.fixture.client.Client
import com.github.hamlet_rt.workoutapp.blackbox.test.action.v1.*
import io.kotest.core.spec.style.FunSpec
import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.shouldBe

fun FunSpec.testApiV1(client: Client, prefix: String = "") {
    context("${prefix}v1") {
        test("Create Tng ok") {
            client.createTng()
        }

        test("Read Tng ok") {
            val created = client.createTng()
            client.readTng(created.id).asClue {
                it shouldBe created
            }
        }

        test("Update Tng ok") {
            val created = client.createTng()
            client.updateTng(created.id, created.lock, TngUpdateObject(title = "Cardio workout"))
            client.readTng(created.id) {
                // TODO раскомментировать, когда будет реальный реп
                //it.ad?.title shouldBe "Selling Nut"
                //it.ad?.description shouldBe someCreateAd.description
            }
        }

        test("Delete Tng ok") {
            val created = client.createTng()
            client.deleteTng(created.id, created.lock)
            client.readTng(created.id) {
                // it should haveError("not-found") TODO раскомментировать, когда будет реальный реп
            }
        }

        test("Search Tng ok") {
            val created1 = client.createTng(someCreateTng.copy(title = "Some Tng"))
            val created2 = client.createTng(someCreateTng.copy(title = "Cardio workout"))


            withClue("Search Tng") {
                client.searchTng(search = TngSearchFilter(searchString = "Tng"))
                // TODO раскомментировать, когда будет реальный реп
                // .shouldExistInOrder({ it.title == "Selling Bolt" })
            }
        }

    }

}