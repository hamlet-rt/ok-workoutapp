package com.github.hamlet_rt.workoutapp.springapp

import com.github.hamlet_rt.workoutapp.backend.repo.sql.RepoTngSQL
import com.ninjasquad.springmockk.MockkBean
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ApplicationTests {

    @MockkBean
    private lateinit var repo: RepoTngSQL
    @Test
    fun contextLoads() {
    }
}
