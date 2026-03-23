package com.finnimcfinger.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "courses")
data class CourseEntity(
    @Id
    val id: Int?,
    val name: String,
    val category: String,
)