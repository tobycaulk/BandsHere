package com.bandshere.service

import com.bandshere.service.common.SessionInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Configuration
class WebMvcConfig : WebMvcConfigurerAdapter() {

    @Autowired
    lateinit var sessionInterceptor: SessionInterceptor

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(sessionInterceptor)
    }
}