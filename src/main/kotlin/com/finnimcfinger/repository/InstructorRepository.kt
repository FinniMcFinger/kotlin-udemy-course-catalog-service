package com.finnimcfinger.repository

import com.finnimcfinger.entity.Instructor
import org.springframework.data.repository.CrudRepository

interface InstructorRepository: CrudRepository<Instructor, Int>