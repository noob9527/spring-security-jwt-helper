package cn.staynoob.springsecurityjwt.exception

import org.springframework.security.core.AuthenticationException

class JwtAuthenticationException(
        message: String?,
        throwable: Throwable? = null
) : AuthenticationException(message, throwable)


