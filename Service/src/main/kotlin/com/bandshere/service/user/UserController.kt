package com.bandshere.service.user

import com.bandshere.service.common.InternalServerErrorException
import com.bandshere.service.common.SessionRequired
import com.bandshere.service.user.request.AuthenticateUserRequest
import com.bandshere.service.user.request.CreateUserRequest
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@CrossOrigin(origins=["*"], maxAge=3600)
@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {

    @PostMapping("/")
    fun create(@Valid @RequestBody request: CreateUserRequest): ResponseEntity<User?> {
        val user: User? = userService.create(request)
        when(user == null) {
            true -> return ResponseEntity(null, null, HttpStatus.INTERNAL_SERVER_ERROR)
            false -> {
                var headers = HttpHeaders()
                headers["session"] = user?.session?.sessionId
                return ResponseEntity(user, headers, HttpStatus.CREATED)
            }
        }
    }

    @SessionRequired
    @GetMapping("/{userId}")
    fun get(@PathVariable("userId") userId: String) = userService.get(userId)

    @SessionRequired
    @PatchMapping("/{userId}/follow/{bandId}")
    fun followBand(@PathVariable("userId") userId: String, @PathVariable("bandId") bandId: String) { }

    @PostMapping("/{userId}/authenticate")
    fun authenticate(@RequestBody request: AuthenticateUserRequest): ResponseEntity<String?> {
        val session = userService.authenticate(request)
        when(session == null) {
            true -> return ResponseEntity(null, null, HttpStatus.INTERNAL_SERVER_ERROR)
            false -> {
                var headers = HttpHeaders()
                headers["session"] = session?.sessionId
                return ResponseEntity(HttpStatus.OK.reasonPhrase, headers, HttpStatus.CREATED)
            }
        }
    }
}