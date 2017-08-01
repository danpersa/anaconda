package com.danix.anaconda.mock

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("dev", "acceptance-tests")
class MockedServices {

    init {
        val wireMockServer = WireMockServer(8089)
        wireMockServer.start()
        WireMock.configureFor(8089)

        // mock the Quote API
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/api/random"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                            {
                                "type":"success",
                                "value":
                                {
                                    "id":5,
                                    "quote":"Spring Boot solves this problem."
                                }
                            }""".trimIndent())))

        // mock the Tokeninfo Service
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/oauth2/tokeninfo"))
                .withHeader("Authorization", WireMock.equalTo("Bearer token1"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                            {
                                "access_token": "token1",
                                "client_id": "ztoken",
                                "cn": "theuser",
                                "expires_in": 3569,
                                "grant_type": "password",
                                "realm": "/employees",
                                "scope": [
                                    "uid"
                                ],
                                "token_type": "Bearer",
                                "uid": "theuser"
                            }
                            """)))
    }
}
