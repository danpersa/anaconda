package com.danix.anaconda

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.context.annotation.Bean
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter
import org.springframework.web.client.RestTemplate
import org.zalando.riptide.OriginalStackTracePlugin
import org.zalando.riptide.Rest


@EnableCircuitBreaker
@SpringBootApplication
open class Application {

    @Bean
    open fun restTemplate(builder: RestTemplateBuilder): RestTemplate {
        return builder
                .setConnectTimeout(2000)
                .setReadTimeout(2000)
                .build()
    }

    @Bean
    open fun rest(): Rest {
        return Rest.builder()
                .requestFactory(HttpComponentsAsyncClientHttpRequestFactory())
                .converter(MappingJackson2HttpMessageConverter())
                .converter(Jaxb2RootElementHttpMessageConverter())
                .plugin(OriginalStackTracePlugin())
                .build()
    }

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
        }
    }
}
