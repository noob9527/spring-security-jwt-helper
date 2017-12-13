package cn.staynoob.demo.domain

import cn.staynoob.springsecurityjwt.domin.JwtPrincipal
import org.springframework.security.core.CredentialsContainer

data class Principal(
        val username: String,
        var password: String?
) : JwtPrincipal, CredentialsContainer {
    override val subject: String
        get() = username

    override fun eraseCredentials() {
        password = null
    }
}