package com.danix.anaconda

import org.springframework.boot.SpringApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.core.env.AbstractEnvironment
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("dev")
class ApplicationDev {

    @Configuration
    @ComponentScan("com.danix.anaconda.mock")
    @Import(MyConfig::class)
    class DevConfig

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "dev")
            SpringApplication.run(DevConfig::class.java, *args)
        }
    }
}
