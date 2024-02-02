package com.github.hamlet_rt.workoutapp.springapp.config

import com.github.hamlet_rt.workoutapp.backend.repo.sql.RepoTngSQL
import com.github.hamlet_rt.workoutapp.backend.repo.sql.SqlProperties
import com.github.hamlet_rt.workoutapp.backend.repository.inmemory.TngRepoStub
import com.github.hamlet_rt.workoutapp.biz.WrkTngProcessor
import com.github.hamlet_rt.workoutapp.common.WrkCorSettings
import com.github.hamlet_rt.workoutapp.common.repo.ITngRepository
import com.github.hamlet_rt.workoutapp.repo.inmemory.TngRepoInMemory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(SqlPropertiesEx::class)
class CorConfig {
    @Bean
    fun processor(corSettings: WrkCorSettings) = WrkTngProcessor(corSettings)


    @Bean
    fun corSettings(
        @Qualifier("prodRepository") prodRepository: ITngRepository?,
        @Qualifier("testRepository") testRepository: ITngRepository,
        @Qualifier("stubRepository") stubRepository: ITngRepository,
    ): WrkCorSettings = WrkCorSettings(
        repoStub = stubRepository,
        repoTest = testRepository,
        repoProd = prodRepository ?: testRepository,
    )

    @Bean(name = ["prodRepository"])
    @ConditionalOnProperty(value = ["prod-repository"], havingValue = "sql")
    fun prodRepository(sqlProperties: SqlProperties) = RepoTngSQL(sqlProperties)

    @Bean
    fun testRepository() = TngRepoInMemory()

    @Bean
    fun stubRepository() = TngRepoStub()
}
