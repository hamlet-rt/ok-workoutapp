rootProject.name = "ok-workoutapp"
include("m1l1-hello")

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings
    val springframeworkBootVersion: String by settings
    val springDependencyManagementVersion: String by settings
    val pluginSpringVersion: String by settings
    val pluginJpa: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion apply false

        kotlin("plugin.serialization") version kotlinVersion apply false
        id("org.springframework.boot") version springframeworkBootVersion apply false
        id("io.spring.dependency-management") version springDependencyManagementVersion apply false
        kotlin("plugin.spring") version pluginSpringVersion apply false
        kotlin("plugin.jpa") version pluginJpa apply false

        id("org.openapi.generator") version openapiVersion apply false
    }
}

include("ok-workoutapp-acceptance")
include("ok-workoutapp-api-v1-jackson")
include("ok-workoutapp-common")
include("ok-workoutapp-mappers-v1")
include("ok-workoutapp-stubs")
include("ok-workoutapp-biz")
include("ok-workoutapp-app-spring")