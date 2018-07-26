package com.bandshere.service.common

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
class InvalidRequestException : Exception("Invalid request")