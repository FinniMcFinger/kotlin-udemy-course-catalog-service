package com.finnimcfinger.dto

import jakarta.validation.constraints.NotBlank

/*
    see documentation on use-site targets:
    https://kotlinlang.org/docs/annotations.html#annotation-use-site-targets
 */

data class CourseDTO(
    val id: Int?,
    @get:NotBlank(message = "CourseDTO.name must not be blank")
    val name: String,
    @get:NotBlank(message = "CourseDTO.category must not be blank")
    val category: String,
)