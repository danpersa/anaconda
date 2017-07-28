package com.danix.anaconda

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.Series.SUCCESSFUL
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.zalando.problem.Problem
import org.zalando.riptide.Bindings
import org.zalando.riptide.Navigators
import org.zalando.riptide.Rest
import java.util.concurrent.CompletableFuture
import javax.ws.rs.core.Response

@Service
open class QuoteService @Autowired constructor(private val restTemplate: RestTemplate,
                                               private val rest: Rest,
                                               private val config: MyConfigurationProperties) {

    @HystrixCommand(
            commandProperties = arrayOf(
                    HystrixProperty(
                            name = "execution.isolation.thread.timeoutInMilliseconds",
                            value = "5000"
                    )
            )
    )
    open fun getQuote(): Quote {
        val quote = restTemplate.getForObject(
                config.quoters, Quote::class.java)
        return quote
    }

    open fun getQuoteRiptide(): CompletableFuture<Quote> {

        val result = CompletableFuture<Quote>()

        try {
            rest.get(config.quoters)
                    .dispatch(Navigators.series(),
                            Bindings.on(SUCCESSFUL).call(Quote::class.java) { quote ->
                                result.complete(quote)
                            },
                            Bindings.anySeries().call { _, _ ->
                                result.completeExceptionally(Problem.valueOf(Response.Status.GONE, "Gone"))
                            })

        } catch (e: Exception) {
            result.completeExceptionally(Problem.valueOf(Response.Status.GONE, "Gone1"))
        }

        return result
    }
}
