package com.finnimcfinger.controller

import com.finnimcfinger.dto.InstructorDTO
import com.finnimcfinger.service.InstructorService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [InstructorController::class])
@AutoConfigureWebTestClient
class InstructorControllerTest {
    @Autowired
    lateinit var webTestClient: WebTestClient
    @MockkBean
    lateinit var mockService: InstructorService

    @Test
    fun createInstructor() {
        val input = InstructorDTO(null, "Doc Brown")

        every {
            mockService.createInstructor(eq(input))
        } returns InstructorDTO(1, "Doc Brown")

        val created = webTestClient.post()
            .uri("/instructors")
            .bodyValue(input)
            .exchange()
            .expectStatus().isCreated
            .expectBody(InstructorDTO::class.java)
            .returnResult()
            .responseBody

        assertNotNull {
            created!!.id
        }
    }
}