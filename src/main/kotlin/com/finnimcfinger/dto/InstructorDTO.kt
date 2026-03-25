package com.finnimcfinger.dto

import jakarta.validation.constraints.NotBlank

data class InstructorDTO(
    val id: Int?,
    @get:NotBlank(message = "InstructorDTO.name is required")
    val name: String,
)