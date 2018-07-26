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
        session ?: return false

        //1 hour session length
        var sessionLength = (60 * 60) * 1000

        var modifiedDate = session.modifiedDate
        modifiedDate ?: return false

        var timeLapsed = modifiedDate.time - Date().time

        return timeLapsed < sessionLength
    }

    fun createSession(userId: String): UserSession? {
        var user = userRepository.findOneByUserId(userId)
        user ?: return null

        var userSession = user.session
        userSession ?: return null

        userSessionRepository.removeBySessionId(userSession.sessionId)
        user.session = UserSession(sessionId = UUID.randomUUID().toString())
        userRepository.save(user)

        return user.session
    }

    fun updateSessionModifiedDate(modifiedDate: Date, sessionId: String) {
        var session = userSessionRepository.findOneBySessionId(sessionId)
        if(session != null) {
            session.modifiedDate = modifiedDate
            userSessionRepository.save(session)
        }
    }
}