package com.finnimcfinger.controller

import com.finnimcfinger.dto.CourseDTO
import com.finnimcfinger.entity.Course
import com.finnimcfinger.repository.CourseRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class CourseControllerIntegrationTest {
    private var savedCourses: MutableList<Course> = mutableListOf();

    @Autowired
    lateinit var webTestClient: WebTestClient
    @Autowired
    lateinit var courseRepository: CourseRepository

    @BeforeEach
    fun setUp() {
        courseRepository.deleteAll()
        savedCourses = mutableListOf()
        getTestCourses().forEach {
            savedCourses.add(courseRepository.save(it))
        }
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

        assertNotNull(created!!.id)
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
            assertEquals(getTestCourses()[index].name, item!!.name)
            assertEquals(getTestCourses()[index].category, item.category)
            assertNotNull(item.id)
        }
    }

    @Test
    fun updateCourse() {
        val original = savedCourses[0]
        val updates = CourseDTO(null, "Updated Course", original.category)
        val returned = webTestClient
            .put()
            .uri("/courses/${original.id}")
            .bodyValue(updates)
            .exchange()
            .expectStatus().isAccepted
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        assertNotNull(returned!!.id)
        assertEquals("Updated Course", returned.name)
        assertEquals(original.category, returned.category)
    }
}