package com.finnimcfinger.controller

import com.finnimcfinger.dto.CourseDTO
import com.finnimcfinger.service.CourseService
import jakarta.validation.Valid
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/courses")
@Validated
class CourseController(val courseService: CourseService) {
    companion object: KLogging()

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    fun addCourse(@RequestBody @Valid courseDTO: CourseDTO): CourseDTO {
        logger.info { "/courses POST" }

        return courseService.addCourse(courseDTO)
    }

    @GetMapping
    fun getAllCourses(): List<CourseDTO> {
        logger.info { "/courses GET" }

        return courseService.getAllCourses();
    }

    @PutMapping("/{courseId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun updateCourse(@PathVariable courseId: Int, @RequestBody courseDTO: CourseDTO): CourseDTO {
        logger.info { "/courses/$courseId PUT" }

        return courseService.updateCourse(courseId, courseDTO)
    }

    @DeleteMapping("/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCourse(@PathVariable courseId: Int) {
        logger.info { "/courses/$courseId DELETE" }

        return courseService.deleteCourse(courseId);
    }
}