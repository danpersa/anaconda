package com.danix.anaconda.acceptance

import com.danix.anaconda.MyConfig
import com.danix.anaconda.Quote
import com.danix.anaconda.Value
import com.danix.anaconda.mock.MockedServices
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod.GET
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(MyConfig::class, MockedServices::class), webEnvironment = RANDOM_PORT)
@ActiveProfiles("acceptance-tests")
internal class QuoteControllerAcceptanceTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun `GET when given a call to quote then returns the right one`() {

        // Given
        val entity = createAuthorizedHttpEntity()

        // When
        val body: Quote = restTemplate.exchange("/api/quote", GET, entity, Quote::class.java).body

        val expectedGreeting = Quote("success", Value(5, "Spring Boot solves this problem."))

        // Then
        assertThat(body).isEqualTo(expectedGreeting)
    }

    private fun createAuthorizedHttpEntity(): HttpEntity<Quote> {
        val headers = HttpHeaders()
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer token1")
        return HttpEntity(headers)
    }
}
