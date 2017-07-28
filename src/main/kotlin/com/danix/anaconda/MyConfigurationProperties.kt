package com.danix.anaconda

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component


@Component
@ConfigurationProperties("service.url")
class MyConfigurationProperties(var quoters: String = "")
