package com.danix.anaconda.mock

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("dev", "acceptance-tests")
open class MockedServices {

    init {
        val wireMockServer = WireMockServer(8089)
        wireMockServer.start()
        WireMock.configureFor(8089)
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/api/random"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"type\":\"success\",\"value\":{\"id\":5,\"quote\":\"Spring Boot solves this problem.\"}}")))
    }
}
