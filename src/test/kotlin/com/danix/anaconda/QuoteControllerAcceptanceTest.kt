package com.danix.anaconda

import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("acceptance-tests")
internal class QuoteControllerAcceptanceTest {

    @Rule
    @JvmField
    val wireMockRule = WireMockRule(8089)

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Before
    fun init() {
        stubFor(get(urlEqualTo("/api/random"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"type\":\"success\",\"value\":{\"id\":5,\"quote\":\"Spring Boot solves this problem.\"}}")));
    }


    @Test
    fun `GET when given a call to quote then returns the right one`() {

        // Given

        // When
        val body: Quote = restTemplate.getForObject("/quote", Quote::class.java)

        val expectedGreeting = Quote("success", Value(5, "Spring Boot solves this problem."))

        // Then
        assertThat(body).isEqualTo(expectedGreeting)
    }
}
