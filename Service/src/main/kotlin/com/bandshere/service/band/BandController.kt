package com.bandshere.service.band

import com.bandshere.service.band.request.CreateBandRequest
import com.bandshere.service.common.SessionRequired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@CrossOrigin(origins=["*"], maxAge=3600)
@RestController
@RequestMapping("/band")
class BandController(private val bandService: BandService) {

    @SessionRequired
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody request: CreateBandRequest, @RequestHeader("session") sessionId: String): Band? = bandService.create(request, sessionId)

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    fun get(@PathVariable("username") username: String): Band? = bandService.get(username)

    @GetMapping("/{username}/followers")
    fun getFollowerCount(@PathVariable("username") username: String): Int = bandService.getFollowerCount(username)
}