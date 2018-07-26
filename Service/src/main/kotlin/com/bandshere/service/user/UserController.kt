package com.bandshere.service.user

import com.bandshere.service.common.BaseController
import com.bandshere.service.user.request.CreateUserRequest
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/user")
class UserController(userService: UserService) : BaseController(userService) {

    @PostMapping("/")
    fun create(@Valid @RequestBody request: CreateUserRequest): ResponseEntity<User?> {
        var user: User? = userService.create(request)
        if(user != null) {
            var headers = HttpHeaders()
            if(user.session != null) {
                var session = user.session!!
                headers.set("session", session.sessionId)
            }

            return ResponseEntity(user, headers, HttpStatus.CREATED)
        } else {
            return ResponseEntity(null, null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    fun get(@PathVariable("userId") userId: String) = userService.get(userId)
}