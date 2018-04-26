package cn.staynoob.springsecurityjwt

import cn.staynoob.springsecurityjwt.exception.JwtAuthenticationException
import cn.staynoob.springsecurityjwt.exception.TokenTypeMismatchException
import io.jsonwebtoken.JwtException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException

open class DefaultJwtAuthenticationProvider : AuthenticationProvider {
    @Autowired
    private lateinit var jwtService: JwtService

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication? {
        val auth = authentication as JwtAuthentication
        if (auth.validatedToken != null) return auth

        val rawToken = jwtService.removeScheme(auth.tokenString)

        val token = try {
            val res = jwtService.parse(rawToken)
            if (res.tokenType != TokenType.ACCESS_TOKEN) {
                throw TokenTypeMismatchException()
            }
            res
        } catch (e: JwtException) {
            throw JwtAuthenticationException(e.message, e)
        }

        return auth.copy(validatedToken = token)
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return authentication == JwtAuthentication::class.java
    }
}