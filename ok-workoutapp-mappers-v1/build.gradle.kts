plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":ok-workoutapp-api-v1-jackson"))
    implementation(project(":ok-workoutapp-common"))

    testImplementation(kotlin("test-junit"))
}