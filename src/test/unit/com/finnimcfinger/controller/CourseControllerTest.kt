package com.finnimcfinger.controller

import com.finnimcfinger.dto.CourseDTO
import com.finnimcfinger.service.CourseService
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class CourseControllerTest {
    private val testCourse = CourseDTO(1, "Test Course", "Unit Tests")

    @MockK
    lateinit var mockService: CourseService

    var controller: CourseController? = null

    @BeforeEach
    fun setup() {
        controller = CourseController(mockService)
    }

    @Test
    fun addCourse() {
        val dto = CourseDTO(null, testCourse.name, testCourse.category)

        every { mockService.addCourse(any<CourseDTO>()) } returns testCourse

        val created = controller?.addCourse(dto)

        assertNotNull(created!!.id)
    }

    @Test
    fun getAllCourses() {
        every { mockService.getAllCourses(any()) } returns listOf(testCourse, testCourse)

        val results = controller?.getAllCourses(null)

        assertEquals(2, results!!.size)
    }

    @Test
    fun updateCourse() {
        val dto = CourseDTO(null, "${testCourse.name} UPDATED", testCourse.category)

        every {
            mockService.updateCourse(any<Int>(), any<CourseDTO>())
        } returns CourseDTO(1, dto.name, dto.category)

        val updated = controller?.updateCourse(1, dto)

        assertEquals(1, updated!!.id)
        assertEquals(dto.name, updated.name)
        assertEquals(dto.category, updated.category)
    }

    @Test
    fun deleteCourse() {
        every { mockService.deleteCourse(any<Int>()) } returns Unit
    }
}