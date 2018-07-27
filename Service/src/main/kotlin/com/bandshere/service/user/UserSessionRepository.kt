package com.bandshere.service.user

import org.springframework.data.repository.CrudRepository
import javax.transaction.Transactional

interface UserSessionRepository : CrudRepository<UserSession, Int> {
    fun findOneBySessionId(sessionId: String): UserSession?

    @Transactional
    fun deleteBySessionId(sessionId: String)
}