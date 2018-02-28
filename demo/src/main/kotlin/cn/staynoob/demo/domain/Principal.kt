package cn.staynoob.demo.domain

import cn.staynoob.springsecurityjwt.JwtPrincipal
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.CredentialsContainer

data class Principal(
        val username: String,
        var password: String?
) : JwtPrincipal, CredentialsContainer {
    @get:JsonIgnore
    override val subject: String
        get() = username

    override fun eraseCredentials() {
        password = null
    }
}