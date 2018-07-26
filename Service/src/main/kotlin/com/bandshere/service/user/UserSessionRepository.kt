package com.bandshere.service.user

import org.springframework.data.repository.CrudRepository

interface UserSessionRepository : CrudRepository<UserSession, Int> {
    fun findOneBySessionId(sessionId: String): UserSession?
}