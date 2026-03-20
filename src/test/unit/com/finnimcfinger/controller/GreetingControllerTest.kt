package com.finnimcfinger.controller

import com.finnimcfinger.service.GreetingService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [GreetingController::class])
@AutoConfigureWebTestClient
class GreetingControllerTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var mockGreetingService: GreetingService

    @Test
    fun retrieveGreeting() {
        val name = "Marty"
        val expected = "$name, hello from default profile!"

        every {
            mockGreetingService.retrieveGreeting(any())
        } returns expected

        val result = webTestClient.get()
            .uri("/greetings/{name}", name)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(String::class.java)
            .returnResult()

        Assertions.assertEquals(expected, result.responseBody)
    }
}