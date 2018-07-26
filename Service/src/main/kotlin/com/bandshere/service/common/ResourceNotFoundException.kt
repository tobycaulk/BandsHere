package com.bandshere.service.common

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value= HttpStatus.NOT_FOUND)
class ResourceNotFoundException : Exception("Resource not found")