package com.github.hamlet_rt.workoutapp.backend.repo.sql

import com.benasher44.uuid.uuid4
import com.github.hamlet_rt.workoutapp.common.models.WrkTng
import org.testcontainers.containers.PostgreSQLContainer
import java.time.Duration

class PostgresContainer : PostgreSQLContainer<PostgresContainer>("postgres:13.2")

object SqlTestCompanion {
    private const val USER = "postgres"
    private const val PASS = "workoutapp-pass"
    private const val SCHEMA = "workoutapp"

    private val container by lazy {
        PostgresContainer().apply {
            withUsername(USER)
            withPassword(PASS)
            withDatabaseName(SCHEMA)
            withStartupTimeout(Duration.ofSeconds(300L))
            start()
        }
    }

    private val url: String by lazy { container.jdbcUrl }

    fun repoUnderTestContainer(
        initObjects: Collection<WrkTng> = emptyList(),
        randomUuid: () -> String = { uuid4().toString() },
    ): RepoTngSQL {
        return RepoTngSQL(
            SqlProperties(url, USER, PASS, SCHEMA, dropDatabase = true),
            initObjects, randomUuid = randomUuid)
    }
}
