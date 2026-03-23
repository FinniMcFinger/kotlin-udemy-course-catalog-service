package com.finnimcfinger.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "course")
data class CourseEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,
    val name: String,
    val category: String,
)