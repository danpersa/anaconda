package com.danix.anaconda.service

import com.danix.anaconda.MyConfigurationProperties
import com.danix.anaconda.Quote
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class QuoteService @Autowired constructor(private val restTemplate: RestTemplate,
                                          private val config: MyConfigurationProperties) {

    @HystrixCommand(
            commandProperties = arrayOf(
                    HystrixProperty(
                            name = "execution.isolation.thread.timeoutInMilliseconds",
                            value = "5000"
                    )
            )
    )
    fun getQuote(): Quote {
        val quote = restTemplate.getForObject(
                config.quoters, Quote::class.java)
        return quote
    }

    @Cacheable("quote")
    fun getCachedQuote(): Quote {
        val quote = restTemplate.getForObject(
                config.quoters, Quote::class.java)
        return quote
    }
}
