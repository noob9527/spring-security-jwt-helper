package cn.staynoob.springsecurityjwt

import org.springframework.security.core.CredentialsContainer

/**
 * the implementation of this interface will be
 * serialize to jwt,
 */
interface JwtPrincipal : CredentialsContainer {
    val subject: String
}