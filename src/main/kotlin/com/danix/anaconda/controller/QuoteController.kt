package com.danix.anaconda.controller

import com.danix.anaconda.MyProblem
import com.danix.anaconda.Quote
import com.danix.anaconda.QuoteService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.zalando.problem.Problem
import org.zalando.problem.ThrowableProblem
import java.util.concurrent.CompletableFuture
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.core.Response.Status

@RestController
@RequestMapping("/api")
class QuoteController @Autowired constructor(val quoteService: QuoteService) {

    private val logger = LoggerFactory.getLogger(QuoteController::class.java)

    @RequestMapping("/quote")
    fun quote(): Quote {

        val quote = quoteService.getQuote()

        logger.debug("Here is a quote {}", quote)

        return quote
    }

    @RequestMapping("/quote-cache")
    fun quoteCache(): Quote {
        return quoteService.getCachedQuote()
    }

    @RequestMapping("/quote1")
    fun quote1(): CompletableFuture<Quote> {
        return quoteService.getQuoteRiptide()
    }

    @RequestMapping("/quote2")
    fun quote2(): CompletableFuture<Quote> {
        throw Problem.valueOf(Status.BAD_REQUEST, "Hello Problem")
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ThrowableProblem::class)
    @ResponseBody
    fun handleProblem(req: HttpServletRequest, problem: ThrowableProblem): MyProblem = MyProblem(problem.title, problem.status)
}
