package com.finnimcfinger.service

import com.finnimcfinger.dto.CourseDTO
import com.finnimcfinger.entity.Course
import com.finnimcfinger.exception.RecordNotFoundException
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

    fun getAllCourses(): List<CourseDTO> {
        return courseRepository.findAll().map { CourseDTO(it.id, it.name, it.category) }
    }

    fun updateCourse(courseId: Int, courseDTO: CourseDTO): CourseDTO {
        val existing = courseRepository.findById(courseId)

        return if (existing.isPresent) {
            logger.info { "course with id: $courseId being updated" }

            existing.get().let {
                it.name = courseDTO.name
                it.category = courseDTO.category
                courseRepository.save(it)
                CourseDTO(it.id, it.name, it.category)
            }
        } else {
            throw RecordNotFoundException("course $courseId not found")
        }
    }

    fun deleteCourse(courseId: Int) {
        val existing = courseRepository.findById(courseId)

        return if (existing.isPresent) {
            courseRepository.deleteById(courseId)
        } else {
            throw RecordNotFoundException("course $courseId not found")
        }
    }
}