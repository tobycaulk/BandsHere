package com.bandshere.service.common

import com.bandshere.service.user.UserService
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import java.lang.Exception
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class SessionInterceptor(private val userService: UserService) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        var handlerMethod: HandlerMethod = handler as HandlerMethod
        var sessionRequired: SessionRequired? = handlerMethod.method.getAnnotation(SessionRequired::class.java)
        sessionRequired ?: return true

        var sessionId = request.getHeader("session")
        sessionId ?: throw InvalidSessionException()
        when(userService.isValidSession(sessionId)) {
            true -> {
                userService.updateSessionModifiedDate(Date(), sessionId)
                return true
            }

            false -> {
                throw InvalidSessionException()
            }
        }
    }

    override fun postHandle(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?, modelAndView: ModelAndView?) { }

    override fun afterCompletion(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?, ex: Exception?) { }
}