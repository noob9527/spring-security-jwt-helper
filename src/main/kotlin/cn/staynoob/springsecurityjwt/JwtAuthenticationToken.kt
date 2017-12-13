package cn.staynoob.springsecurityjwt

import cn.staynoob.springsecurityjwt.domin.JwtPrincipal
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

/**
 *
 */
class JwtAuthenticationToken(
        private var principal: JwtPrincipal,
        authorities: Collection<GrantedAuthority>? = null
) : AbstractAuthenticationToken(authorities) {
    override fun getPrincipal() = principal
    override fun getCredentials(): Any? = null
}