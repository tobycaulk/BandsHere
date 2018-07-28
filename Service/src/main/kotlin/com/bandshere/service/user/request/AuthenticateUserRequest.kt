package com.bandshere.service.user.request

data class AuthenticateUserRequest(
        val email: String = "",
        val password: String = ""
)