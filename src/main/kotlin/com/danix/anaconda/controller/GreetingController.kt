package com.danix.anaconda.controller

import com.codahale.metrics.annotation.Timed
import com.danix.anaconda.Greeting
import com.danix.anaconda.GreetingService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.metrics.CounterService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GreetingController @Autowired constructor(val greetingService: GreetingService,
                                                val counterService: CounterService) {

    @Timed(name = "timer.greeting", absolute = true)
    @RequestMapping("/greeting")
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String): Greeting {

        logger.debug("Here is a greeting for {}", name)
        counterService.increment(meterGreeting)
        return greetingService.sayHello(name)
    }

    private companion object {
        val logger = LoggerFactory.getLogger(GreetingController::class.java)!!
        const val meterGreeting = "meter.greeting"
    }
}
