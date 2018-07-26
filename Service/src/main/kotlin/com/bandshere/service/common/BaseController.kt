package com.bandshere.service.common

import com.bandshere.service.user.UserService
import com.bandshere.service.user.UserSession

open class BaseController(val userService: UserService) {

    fun isValidSession(sessionId: String): Boolean {
        return userService.isValidSession(sessionId)
    }

    fun createSession(userId: String): UserSession? {
        return null
    }
}