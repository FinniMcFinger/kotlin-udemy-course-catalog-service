package com.finnimcfinger.controller

import com.finnimcfinger.dto.InstructorDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class InstructorControllerIntegrationTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun createInstructor() {
        val dto = InstructorDTO(null, "Doc Brown")
        val created = webTestClient.post()
            .uri("/instructors")
            .bodyValue(dto)
            .exchange()
            .expectStatus().isCreated
            .expectBody(InstructorDTO::class.java)
            .returnResult()
            .responseBody

        assertNotNull(created!!.id)
    }

    @Test
    fun createInstructor_validationError() {
        val dto = InstructorDTO(null, "")
        val response = webTestClient.post()
            .uri("/instructors")
            .bodyValue(dto)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals("InstructorDTO.name is required", response)
    }
}