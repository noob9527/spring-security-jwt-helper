package cn.staynoob.springsecurityjwt

import java.time.Instant

data class Token(
        val value: String,
        val tokenType: TokenType,
        val claims: JwtClaims
) {
    val subject: String = claims.subject
    val expiration: Instant? = claims.expiration?.toInstant()
}
