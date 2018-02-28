package cn.staynoob.springsecurityjwt

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class JwtAuthenticationToken(
        private var principal: JwtPrincipal,
        authorities: Collection<GrantedAuthority>? = null
) : AbstractAuthenticationToken(authorities) {

    init {
        this.isAuthenticated = false
    }

    override fun getPrincipal() = principal
    override fun getCredentials(): Any? = null
}