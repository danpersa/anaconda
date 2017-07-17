package com.danix.anaconda

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.zalando.riptide.Bindings
import org.zalando.riptide.Navigators
import org.zalando.riptide.Rest
import org.springframework.http.HttpStatus.Series.SUCCESSFUL
import org.springframework.http.HttpStatus.Series.SERVER_ERROR
import org.springframework.http.HttpStatus.Series.CLIENT_ERROR
import org.springframework.web.context.request.async.DeferredResult
import org.zalando.problem.Problem
import org.zalando.riptide.problem.ProblemRoute
import java.util.concurrent.CompletableFuture
import javax.ws.rs.core.Response

@Service
open class QuoteService @Autowired constructor(private val restTemplate: RestTemplate,
                                               private val rest: Rest) {

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
                "http://gturnquist-quoters.cfapps.io/api/random", Quote::class.java)
        return quote
    }

    open fun getQuoteRiptide(): CompletableFuture<Quote> {

        val result = CompletableFuture<Quote>()

        try {
            rest.get("http://gturnquist-quoters.cfapps.io/api/random")
                    .dispatch(Navigators.series(),
                            Bindings.on(SUCCESSFUL).call(Quote::class.java) { quote ->
                                println("Quote $quote")
                                result.complete(quote)
                            },
                            Bindings.anySeries().call { response, reader ->
                                result.completeExceptionally(Problem.valueOf(Response.Status.GONE, "Gone"))
                            })

        } catch (e: Exception) {
            result.completeExceptionally(Problem.valueOf(Response.Status.GONE, "Gone1"))
        }

        return result
    }
}
