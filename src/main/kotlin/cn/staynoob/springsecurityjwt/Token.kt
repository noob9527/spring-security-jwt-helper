package cn.staynoob.springsecurityjwt

import com.fasterxml.jackson.annotation.JsonValue
import java.time.Instant

data class Token(
        @get: JsonValue
        val value: String,
        val tokenType: TokenType,
        val claims: JwtClaims
) {
    val subject: String = claims.subject
    val expiration: Instant? = claims.expiration?.toInstant()
}
