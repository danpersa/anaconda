package com.danix.anaconda

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong

@Service
open class GreetingService {

    private val counter = AtomicLong()

    @HystrixCommand
    open fun sayHello(name: String): Greeting {
        return Greeting(counter.incrementAndGet(), "Hello, $name")
    }
}
