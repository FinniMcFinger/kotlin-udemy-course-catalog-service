package com.finnimcfinger.repository

import com.finnimcfinger.entity.Course
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import java.util.stream.Stream

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

    @Test
    fun findCoursesByName() {
        val courses = courseRepository.findCoursesByName("Test")

        assertEquals(2, courses.size)
    }

    @ParameterizedTest
    @MethodSource("courseAndSize")
    fun findByNameContaining_parameterized(name: String, size: Int) {

        val courses = courseRepository.findByNameContaining(name)

        assertEquals(size, courses.size)
    }

    companion object {
        @JvmStatic
        fun courseAndSize(): Stream<Arguments> {
            return Stream.of(Arguments.arguments("Test", 2),
                Arguments.arguments("1", 1))
        }
    }
}