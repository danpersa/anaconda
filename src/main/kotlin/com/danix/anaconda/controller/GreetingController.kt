package com.danix.anaconda.controller

import com.danix.anaconda.Greeting
import com.danix.anaconda.GreetingService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GreetingController @Autowired constructor(val greetingService: GreetingService) {

    private val logger = LoggerFactory.getLogger(GreetingController::class.java)

    @RequestMapping("/greeting")
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String): Greeting {

        logger.info("Here is a greeting for {}", name)

        return greetingService.sayHello(name)
    }
}
