package com.bandshere.service.common

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value= HttpStatus.CONFLICT)
class EmailAlreadyRegisteredException : Exception("Email is already registered")