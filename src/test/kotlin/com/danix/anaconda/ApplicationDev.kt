package com.danix.anaconda

import org.springframework.boot.SpringApplication
import org.springframework.core.env.AbstractEnvironment
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("dev")
open class ApplicationDev {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            MockedServices.init()
            MockedServices.start()
            System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "dev")
            SpringApplication.run(MyConfig::class.java, *args)
        }
    }
}
