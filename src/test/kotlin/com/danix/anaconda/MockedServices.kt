package com.danix.anaconda

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo

object MockedServices {

    fun init() {
        val wireMockServer = WireMockServer(8089)
        wireMockServer.start()
    }

    fun start() {
        WireMock.configureFor(8089)
        stubFor(get(urlEqualTo("/api/random"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"type\":\"success\",\"value\":{\"id\":5,\"quote\":\"Spring Boot solves this problem.\"}}")))
    }
}
