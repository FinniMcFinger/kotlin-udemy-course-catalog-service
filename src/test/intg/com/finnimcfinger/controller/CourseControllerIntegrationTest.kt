package com.finnimcfinger.controller

import com.finnimcfinger.dto.CourseDTO
import com.finnimcfinger.entity.Course
import com.finnimcfinger.entity.Instructor
import com.finnimcfinger.repository.CourseRepository
import com.finnimcfinger.repository.InstructorRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.util.UriComponentsBuilder

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class CourseControllerIntegrationTest {
    private var savedCourses: MutableList<Course> = mutableListOf();

    @Autowired
    lateinit var webTestClient: WebTestClient
    @Autowired
    lateinit var courseRepository: CourseRepository
    @Autowired
    lateinit var instructorRepository: InstructorRepository

    @BeforeEach
    fun setUp() {
        courseRepository.deleteAll()
        savedCourses = mutableListOf()
        getTestCourses().forEach {
            savedCourses.add(courseRepository.save(it))
        }
    }

    fun getTestCourses(): List<Course> {
        val instructor = Instructor(null, "Mr. Doctor")
        instructorRepository.save(instructor)

        return listOf(
            Course(null, "Test Course 1", "Integration Tests", instructor),
            Course(null, "Test Course 2", "Integration Tests", instructor),
        )
    }

    @Test
    fun addCourse() {
        val dto = CourseDTO(null, "INT Test Course", "Integration Tests",
            savedCourses[0].instructor!!.id)
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
    fun addCourse_validationError() {
        val dto = CourseDTO(null, "", "", savedCourses[0].instructor!!.id)
        val response = webTestClient
            .post()
            .uri("/courses")
            .bodyValue(dto)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals("CourseDTO.category must not be blank, CourseDTO.name must not be blank", response)
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
    fun getAllCoursesByName() {
        val uri = UriComponentsBuilder.fromUriString("/courses")
            .queryParam("courseName", "Test")
            .toUriString()
        val returned = webTestClient
            .get()
            .uri(uri)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        assertEquals(2, returned!!.size)
    }

    @Test
    fun updateCourse() {
        val original = savedCourses[0]
        val updates = CourseDTO(null, "Updated Course", original.category, original.instructor!!.id)
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

    @Test
    fun updateCourse_notFound() {
        val original = savedCourses[0]
        val updates = CourseDTO(null, "Updated Course", original.category, original.instructor!!.id)
        val returned = webTestClient
            .put()
            .uri("/courses/10000")
            .bodyValue(updates)
            .exchange()
            .expectStatus().isNotFound
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals("course 10000 not found", returned!!)
    }

    @Test
    fun deleteCourse() {
        val original = savedCourses[0]
        webTestClient
            .delete()
            .uri("/courses/${original.id}")
            .exchange()
            .expectStatus().isNoContent
    }
}