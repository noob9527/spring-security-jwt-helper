package cn.staynoob.springsecurityjwt

import cn.staynoob.springsecurityjwt.service.JwtPrincipalService
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Component

/**
 * Created by xy on 17-1-9.
 */
@Component
class JwtAuthenticationProvider : AuthenticationProvider {

    private val log = Logger.getLogger(JwtAuthenticationProvider::class.java)

    @Autowired
    private lateinit var jwtPrincipalService: JwtPrincipalService

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication? {
        val jwtAuthenticationToken = authentication as JwtAuthenticationToken
        val jwtPrincipal = jwtAuthenticationToken.principal
        val user = jwtPrincipalService.loadUser(jwtPrincipal)
                ?: throw AuthenticationCredentialsNotFoundException("subject:${jwtPrincipal.subject} not found")
        log.info(String.format("%s authenticate success", user.username))
        return JwtAuthenticationToken(user, user.authorities)
                .apply { isAuthenticated = true }
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication == JwtAuthenticationToken::class.java
    }
}
