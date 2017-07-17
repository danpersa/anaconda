package com.danix.anaconda

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture

@RestController
class QuoteController @Autowired constructor(val quoteService: QuoteService) {

    @RequestMapping("/quote")
    fun quote(): Quote {
        return quoteService.getQuote()
    }

    @RequestMapping("/quote1")
    fun quote1(): CompletableFuture<Quote> {
        return quoteService.getQuoteRiptide()
    }
}
