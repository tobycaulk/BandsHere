package com.bandshere.service.common

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
class InternalServerErrorException : Exception {
    constructor(message: String) : super(message)
    constructor() : super("Internal server error")
}