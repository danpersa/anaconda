package com.danix.anaconda.acceptance

import com.fasterxml.jackson.annotation.JsonProperty
import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class HealthEndpointAcceptanceTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun `GET returns "UP"`() {

        // Given

        // When
        val body = restTemplate.getForObject("/health", Health::class.java)

        // Then
        Assertions.assertThat(body).isEqualTo(Health("UP"))
    }

    internal data class Health(@JsonProperty("status") val status: String)
}
