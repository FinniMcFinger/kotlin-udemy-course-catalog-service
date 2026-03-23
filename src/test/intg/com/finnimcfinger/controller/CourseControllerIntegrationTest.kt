package com.finnimcfinger.controller

import com.finnimcfinger.dto.CourseDTO
import com.finnimcfinger.entity.Course
import com.finnimcfinger.repository.CourseRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class CourseControllerIntegrationTest {

    @Autowired
    lateinit var webTestClient: WebTestClient
    @Autowired
    lateinit var courseRepository: CourseRepository

    @BeforeEach
    fun setUp() {
        courseRepository.deleteAll()
        courseRepository.saveAll(getTestCourses())
    }

    fun getTestCourses(): List<Course> = listOf(
        Course(null, "Test Course 1", "Integration Tests"),
        Course(null, "Test Course 2", "Integration Tests"),
    )

    @Test
    fun addCourse() {
        val dto = CourseDTO(null, "INT Test Course", "Integration Tests")
        val created = webTestClient
            .post()
            .uri("/courses")
            .bodyValue(dto)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertNotNull(created!!.id)
    }

    @Test
    fun getAllCourses() {
        val returned = webTestClient
            .get()
            .uri("/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        returned!!.forEachIndexed { index, item ->
            Assertions.assertEquals(getTestCourses()[index].name, item!!.name)
            Assertions.assertEquals(getTestCourses()[index].category, item.category)
            Assertions.assertNotNull(item.id)
        }
    }
}