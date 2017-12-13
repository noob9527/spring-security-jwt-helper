package cn.staynoob.demo.api

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/secret"], produces = [(MediaType.APPLICATION_JSON_UTF8_VALUE)])
class SecretCtrl {

    data class Secret(
            val content: String = "secret"
    )

    @GetMapping
    fun getSecret() = Secret()
}