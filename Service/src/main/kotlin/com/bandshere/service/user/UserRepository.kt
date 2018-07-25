package com.bandshere.service.user

import org.springframework.data.repository.CrudRepository

interface UserRepository: CrudRepository<User, Int> {
    fun findOneByEmail(email: String): User?
    fun findOneByUserId(userId: String): User?
}