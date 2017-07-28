package com.danix.anaconda

import org.springframework.boot.SpringApplication
import org.springframework.core.env.AbstractEnvironment

class Application {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "prod")
            SpringApplication.run(MyConfig::class.java, *args)
        }
    }
}
