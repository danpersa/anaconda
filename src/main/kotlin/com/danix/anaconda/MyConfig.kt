package com.danix.anaconda

import com.ryantenney.metrics.spring.config.annotation.EnableMetrics
import org.springframework.boot.actuate.metrics.rich.InMemoryRichGaugeRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.web.client.RestTemplate

@EnableHystrixDashboard
@EnableCircuitBreaker
@SpringBootApplication
@EnableCaching
@EnableMetrics(proxyTargetClass = true)
class MyConfig {

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate {
        return builder
                .setConnectTimeout(2000)
                .setReadTimeout(2000)
                .build()
    }

    @Bean
    @Primary
    // to be able to have metrics like averages
    fun inMemoryRichGaugeRepository() = InMemoryRichGaugeRepository()
}
