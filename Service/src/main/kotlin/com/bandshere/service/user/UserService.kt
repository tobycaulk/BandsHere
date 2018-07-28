package com.bandshere.service.user

import com.bandshere.service.common.EmailAlreadyRegisteredException
import com.bandshere.service.common.InternalServerErrorException
import com.bandshere.service.common.InvalidRequestException
import com.bandshere.service.common.ResourceNotFoundException
import com.bandshere.service.user.request.AuthenticateUserRequest
import com.bandshere.service.user.request.CreateUserRequest
import org.mindrot.jbcrypt.BCrypt
import org.postgresql.util.PSQLException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
        private val userRepository: UserRepository,
        private val userSessionRepository: UserSessionRepository
) {

    fun create(request: CreateUserRequest): User? {
        val user: User?
        try {
            user = userRepository.save(User(
                    userId = UUID.randomUUID().toString(),
                    email = request.email,
                    username = request.username,
                    password = BCrypt.hashpw(request.password, BCrypt.gensalt()),
                    session = UserSession(sessionId = UUID.randomUUID().toString())
            ))
        } catch(e: DataIntegrityViolationException) {
            throw EmailAlreadyRegisteredException()
        } catch(e: Exception) {
            throw InternalServerErrorException("Error while registering user", e)
        }

        return user
    }

    fun get(userId: String) = userRepository.findOneByUserId(userId)

    fun isValidSession(sessionId: String): Boolean {
        val session = userSessionRepository.findOneBySessionId(sessionId)
        session ?: return false

        //1 hour session length
        val sessionLength = (60 * 60) * 1000

        val modifiedDate = session.modifiedDate
        modifiedDate ?: return false

        val timeLapsed = Date().time - modifiedDate.time

        return timeLapsed < sessionLength
    }

    fun createSession(userId: String): UserSession? {
        val user = userRepository.findOneByUserId(userId)
        user ?: throw InvalidRequestException()

        when(user.session == null) {
            true -> {
                user.session = UserSession(sessionId = UUID.randomUUID().toString())
                userRepository.save(user)
            }

            false -> {
                if(user.session?.sessionId != null && !isValidSession(user.session!!.sessionId)) {
                    user.session = UserSession(sessionId = UUID.randomUUID().toString())
                    userRepository.save(user)
                }
            }
        }

        return user.session
    }

    fun updateSessionModifiedDate(modifiedDate: Date, sessionId: String) {
        var session = userSessionRepository.findOneBySessionId(sessionId)
        if(session != null) {
            session.modifiedDate = modifiedDate
            userSessionRepository.save(session)
        }
    }

    fun authenticate(request: AuthenticateUserRequest): UserSession? {
        if(request.email.isBlank() || request.password.isBlank()) {
            throw InvalidRequestException()
        }

        val email = request.email.trim()
        val password = request.password.trim()

        val user = userRepository.findOneByEmail(email)
        user ?: throw ResourceNotFoundException()

        val storedPassword = user.password
        if(!BCrypt.checkpw(password, storedPassword)) {
            throw InvalidRequestException()
        }

        return createSession(user.userId)
    }

    fun signout(sessionId: String) {
        val session = userSessionRepository.findOneBySessionId(sessionId)
        session ?: throw InternalServerErrorException()

        val user = session.user
        user ?: throw InternalServerErrorException()

        user.session = null
        userRepository.save(user)

        userSessionRepository.save(session)
        userSessionRepository.delete(session)
    }
}