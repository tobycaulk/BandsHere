package com.bandshere.service.user.request

data class CreateUserRequest(
        val email: String = "",
        val username: String = "",
        val password: String = ""
)