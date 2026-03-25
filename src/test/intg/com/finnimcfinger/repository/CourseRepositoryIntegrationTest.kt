package com.finnimcfinger.repository

import com.finnimcfinger.entity.Course
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@ActiveProfiles("test")
class CourseRepositoryIntegrationTest {
    private var savedCourses: MutableList<Course> = mutableListOf();

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
    fun findByNameContaining() {
        val courses = courseRepository.findByNameContaining("Test")

        assertEquals(2, courses.size)
    }
}