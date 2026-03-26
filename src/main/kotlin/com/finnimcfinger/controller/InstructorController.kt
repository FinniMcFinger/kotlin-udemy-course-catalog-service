package com.finnimcfinger.controller

import com.finnimcfinger.dto.InstructorDTO
import com.finnimcfinger.service.InstructorService
import jakarta.validation.Valid
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/instructors")
@Validated
class InstructorController(val instructorService: InstructorService) {
    companion object: KLogging()

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    fun createInstructor(@RequestBody @Valid instructorDto: InstructorDTO): InstructorDTO {
        logger.info { "/instructors POST" }

        return instructorService.createInstructor(instructorDto);
    }
}