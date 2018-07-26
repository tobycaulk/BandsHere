package com.bandshere.service.user.request

data class AuthenticateUserRequest(
        val username: String = "",
        val password: String = ""
)