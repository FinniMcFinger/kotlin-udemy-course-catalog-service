package com.finnimcfinger.service

import com.finnimcfinger.dto.InstructorDTO
import com.finnimcfinger.entity.Instructor
import com.finnimcfinger.repository.InstructorRepository
import mu.KLogging
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class InstructorService(val instructorRepository: InstructorRepository) {
    companion object: KLogging()

    fun createInstructor(instructorDto: InstructorDTO): InstructorDTO {
        val instructorEntity = Instructor(null, instructorDto.name)

        instructorRepository.save(instructorEntity)
        logger.info { "saved instructor: $instructorEntity" }

        return instructorEntity.let {
            InstructorDTO(it.id, it.name)
        }
    }

    fun findInstructorById(id: Int): Optional<Instructor?> {
        return instructorRepository.findById(id)
    }
}