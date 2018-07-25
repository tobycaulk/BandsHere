package com.bandshere.service.band

import com.bandshere.service.band.request.CreateBandRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/band")
class BandController(private val bandService: BandService) {

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody request: CreateBandRequest): Band? = bandService.create(request)

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    fun get(@PathVariable("username") username: String): Band? = bandService.get(username)
}