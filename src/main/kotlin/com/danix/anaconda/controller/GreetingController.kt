package com.danix.anaconda.controller

import com.danix.anaconda.Greeting
import com.danix.anaconda.GreetingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GreetingController @Autowired constructor(val greetingService: GreetingService) {

    @RequestMapping("/greeting")
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String): Greeting {
        return greetingService.sayHello(name)
    }
}
