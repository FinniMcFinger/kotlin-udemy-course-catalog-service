package com.finnimcfinger.repository

import com.finnimcfinger.entity.Course
import org.springframework.data.repository.CrudRepository

interface CourseRepository: CrudRepository<Course, Int> {
}