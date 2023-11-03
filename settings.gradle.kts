rootProject.name = "ok-workoutapp"
include("m1l1-hello")

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion apply false

        kotlin("plugin.serialization") version kotlinVersion apply false

        id("org.openapi.generator") version openapiVersion apply false
    }
}

include("ok-workoutapp-acceptance")
include("ok-workoutapp-api-v1-jackson")
include("ok-workoutapp-common")
include("ok-workoutapp-mappers-v1")