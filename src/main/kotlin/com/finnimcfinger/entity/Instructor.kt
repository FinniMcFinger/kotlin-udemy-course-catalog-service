package com.finnimcfinger.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "instructor")
data class Instructor(
    @Id
    @GeneratedValue(GenerationType.AUTO)
    val id: Int?,
    var name: String,
)