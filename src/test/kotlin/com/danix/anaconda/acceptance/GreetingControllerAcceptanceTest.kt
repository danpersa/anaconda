package com.danix.anaconda.acceptance

import com.danix.anaconda.Greeting
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class GreetingControllerAcceptanceTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun `GET when given Duke then returns "Hello, Duke"`() {

        // Given
        val name = "Duke"

        // When
        val body: Greeting = restTemplate.getForObject("/greeting/?name={name}", Greeting::class.java, name)

        val expectedGreeting = Greeting(1, "Hello, $name")

        // Then
        assertThat(body).isEqualTo(expectedGreeting)
    }
}
