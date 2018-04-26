package cn.staynoob.demo.api

import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/secret"], produces = [(MediaType.APPLICATION_JSON_UTF8_VALUE)])
@PreAuthorize("isAuthenticated()")
class SecretCtrl {

    data class Secret(
            val content: String = "secret"
    )

    @GetMapping
    fun getSecret() = SecurityContextHolder.getContext().authentication?.principal
}