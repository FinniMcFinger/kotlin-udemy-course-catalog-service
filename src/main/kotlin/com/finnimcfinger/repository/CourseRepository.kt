package com.finnimcfinger.repository

import com.finnimcfinger.entity.Course
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

/*
    See query creation documentation:
    https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
 */

interface CourseRepository: CrudRepository<Course, Int> {
    fun findByNameContaining(name: String): List<Course>

    @Query(value = "SELECT * FROM course WHERE name LIKE %?1%", nativeQuery = true)
    fun findCoursesByName(name: String): List<Course>
}