package com.ep.challenge.exceptions

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus.*
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpClientErrorException
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class GlobalExceptionHandler {
    val logger = KotlinLogging.logger {}

    @ExceptionHandler(Throwable::class)
    fun handleError(request: HttpServletRequest, exception: Throwable): ResponseEntity<ErrorInfo> {
        logger.error(exception.message, exception)

        val (code, message) = when (exception) {
            is BadRequestException -> BAD_REQUEST to exception.message
            is ForbiddenException -> FORBIDDEN to exception.message
            is ConflictException -> CONFLICT to exception.message
            is NotFoundException -> NOT_FOUND to exception.message
            is HttpClientErrorException -> NOT_FOUND to exception.message

            else -> throw exception
        }

        val errorInfo = ErrorInfo(error = message!!, path = request.requestURI)
        val headers = HttpHeaders()
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
        return ResponseEntity(errorInfo, headers, code)
    }
}

data class ErrorInfo(
    val error: String,
    val path: String
)

@JsonIgnoreProperties("stackTrace", "cause", "suppressed")
class NotFoundException(override val message: String = "Not Found") : RuntimeException()

@JsonIgnoreProperties("stackTrace", "cause", "suppressed")
class ConflictException(override val message: String = "Not Found") : RuntimeException()

@JsonIgnoreProperties("stackTrace", "cause", "suppressed")
class ForbiddenException(override val message: String = "Forbidden") : RuntimeException()

@JsonIgnoreProperties("stackTrace", "cause", "suppressed")
class BadRequestException(override val message: String = "Bad Request") : RuntimeException()