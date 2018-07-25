package com.bandshere.service.user

import com.bandshere.service.user.request.CreateUserRequest
import org.mindrot.jbcrypt.BCrypt
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val userRepository: UserRepository) {

    fun create(request: CreateUserRequest): User? {
        return userRepository.save(User(
                userId = UUID.randomUUID().toString(),
                email = request.email,
                username = request.username,
                password = BCrypt.hashpw(request.password, BCrypt.gensalt())
        ))
    }

    fun get(userId: String) = userRepository.findOneByUserId(userId)
}