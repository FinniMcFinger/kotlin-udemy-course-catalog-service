package com.finnimcfinger.repository

import com.finnimcfinger.entity.CourseEntity
import org.springframework.data.repository.CrudRepository

interface CourseRepository: CrudRepository<CourseEntity, Int> {
}