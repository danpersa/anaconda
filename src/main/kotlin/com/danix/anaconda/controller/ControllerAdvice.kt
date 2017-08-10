package com.danix.anaconda.controller

import com.danix.anaconda.MyProblem
import com.danix.anaconda.service.DatabaseTimeoutException
import com.netflix.hystrix.exception.HystrixRuntimeException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.zalando.problem.DefaultProblem
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.core.Response.Status

@ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(HystrixRuntimeException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun handleHystrix(req: HttpServletRequest, ex: HystrixRuntimeException): MyProblem {

        logger.error("Handle hystrix", ex.fallbackException)

        val cause = ex.fallbackException?.cause

        when (cause) {
            is DefaultProblem -> return MyProblem(cause.title, cause.status)
            is DatabaseTimeoutException -> return MyProblem(cause.message, Status.INTERNAL_SERVER_ERROR)
            else -> {
                return MyProblem("Hystrix failed", Status.INTERNAL_SERVER_ERROR)
            }
        }
    }

    private companion object {
        private val logger = LoggerFactory.getLogger(ControllerAdvice::class.java)!!
    }
}
