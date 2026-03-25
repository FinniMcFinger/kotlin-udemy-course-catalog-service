package com.finnimcfinger.repository

import com.finnimcfinger.entity.Course
import org.springframework.data.repository.CrudRepository

/*
    See query creation documentation:
    https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
 */

interface CourseRepository: CrudRepository<Course, Int> {
    fun findByNameContaining(name: String): List<Course>
}