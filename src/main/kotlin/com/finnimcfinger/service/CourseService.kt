package com.finnimcfinger.service

import com.finnimcfinger.dto.CourseDTO
import com.finnimcfinger.entity.Course
import com.finnimcfinger.repository.CourseRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class CourseService(val courseRepository: CourseRepository) {
    companion object: KLogging()

    fun addCourse(courseDto: CourseDTO): CourseDTO {
        val courseEntity = courseDto.let {
            Course(null, it.name, it.category)
        }

        courseRepository.save(courseEntity);
        logger.info { "saved course: $courseEntity" }

        return courseEntity.let {
            CourseDTO(it.id, it.name, it.category)
        }
    }
}