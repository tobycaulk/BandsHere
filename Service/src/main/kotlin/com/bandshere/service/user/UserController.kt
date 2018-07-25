package com.bandshere.service.user

import com.bandshere.service.user.request.CreateUserRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody request: CreateUserRequest) = userService.create(request)

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    fun get(@PathVariable("userId") userId: String) = userService.get(userId)
}