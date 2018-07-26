package com.bandshere.service.user

import com.bandshere.service.user.request.CreateUserRequest
import org.mindrot.jbcrypt.BCrypt
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
        private val userRepository: UserRepository,
        private val userSessionRepository: UserSessionRepository
) {

    fun create(request: CreateUserRequest): User? {
        return userRepository.save(User(
                userId = UUID.randomUUID().toString(),
                email = request.email,
                username = request.username,
                password = BCrypt.hashpw(request.password, BCrypt.gensalt()),
                session = UserSession(sessionId = UUID.randomUUID().toString())
        ))
    }

    fun get(userId: String) = userRepository.findOneByUserId(userId)

    fun isValidSession(sessionId: String): Boolean {
        var session = userSessionRepository.findOneBySessionId(sessionId)
        if(session == null) {
            return false
        }

        //1 hour session length
        var sessionLength = (60 * 60) * 1000

        var creationDate = session.creationDate
        var modifiedDate = session.modifiedDate
        if(creationDate == null || modifiedDate == null) {
            return false
        }

        var timeLapsed = modifiedDate.time - creationDate.time

        return timeLapsed < sessionLength
    }
}