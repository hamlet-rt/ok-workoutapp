package com.github.hamlet_rt.workoutapp.springapp.config

import com.github.hamlet_rt.workoutapp.biz.WrkTngProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CorConfig {
    @Bean
    fun processor() = WrkTngProcessor()
}
