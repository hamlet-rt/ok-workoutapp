package com.github.hamlet_rt.workoutapp.backend.repo.sql

open class SqlProperties(
    val url: String = "jdbc:postgresql://localhost:5432/workoutapp",
    val user: String = "postgres",
    val password: String = "workoutapp-pass",
    val schema: String = "workoutapp",
    // Delete tables at startup - needed for testing
    val dropDatabase: Boolean = false,
)
