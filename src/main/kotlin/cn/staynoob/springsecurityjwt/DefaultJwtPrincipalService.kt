package cn.staynoob.springsecurityjwt

import org.springframework.security.core.GrantedAuthority

class DefaultJwtPrincipalService : JwtPrincipalService {
    override fun loadAuthorities(principal: JwtPrincipal): Collection<GrantedAuthority>? {
        return setOf()
    }

    override fun loadPrincipal(principal: JwtPrincipal): JwtPrincipal? {
        return principal
    }
}
