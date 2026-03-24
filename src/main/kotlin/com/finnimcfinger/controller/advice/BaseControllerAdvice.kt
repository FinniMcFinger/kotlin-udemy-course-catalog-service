package com.finnimcfinger.controller.advice

import com.finnimcfinger.exception.RecordNotFoundException
import mu.KLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@Component
@ControllerAdvice
class BaseControllerAdvice : ResponseEntityExceptionHandler() {
    companion object { val log = KLogging().logger("BaseControllerAdvice") }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<in Any>? {
        log.error("MethodArgumentNotValidException observed: ${ex.message}", ex)
        val errors = ex.bindingResult.allErrors
            .map { err -> err.defaultMessage!! }
            .sorted()
        log.info { "errors: $errors" }

        return ResponseEntity(errors.joinToString(", "), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(RecordNotFoundException::class)
    fun handleNoSuchElementException(ex: Exception, request: WebRequest): ResponseEntity<Any?> {
        log.info("NoSuchElementException observed: ${ex.message}", ex)

        return ResponseEntity(ex.message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(Exception::class)
    fun generalExceptionHandler(ex: Exception, request: WebRequest): ResponseEntity<Any?> {
        log.error("Exception observed: ${ex.message}", ex)

        return ResponseEntity("Something went wrong, please contact support", HttpStatus.INTERNAL_SERVER_ERROR)
    }
}