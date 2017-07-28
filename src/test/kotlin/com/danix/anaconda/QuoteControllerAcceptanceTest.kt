package com.danix.anaconda

import com.danix.anaconda.mock.MockedServices
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
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

        // When
        val body: Quote = restTemplate.getForObject("/quote", Quote::class.java)

        val expectedGreeting = Quote("success", Value(5, "Spring Boot solves this problem."))

        // Then
        assertThat(body).isEqualTo(expectedGreeting)
    }
}
