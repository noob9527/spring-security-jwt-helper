package cn.staynoob.springsecurityjwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts

class JwtClaims private constructor(
        private val claims: Claims
) : Claims by claims {
    companion object {
        fun of(
                subject: String,
                issuer: String? = null,
                audience: String? = null,
                payload: Map<String, Any?>? = null
        ): JwtClaims {
            val claims = Jwts.claims().apply {
                payload?.let { putAll(it) }
                setSubject(subject)
                setIssuer(issuer)
                setAudience(audience)
            }
            return JwtClaims(claims)
        }

        internal fun fromClaims(claims: Claims): JwtClaims {
            return JwtClaims(claims)
        }
    }
}

