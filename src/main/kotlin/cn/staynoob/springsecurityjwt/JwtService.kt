package cn.staynoob.springsecurityjwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.apache.log4j.Logger
import java.util.*

/**
 * Created by xy on 16-10-7.
 */
class JwtService(
        private val secret: String,
        private val issuer: String?,
        private val audience: String?,
        private val expiration: Long,
        private val ignoreTokenExpiration: Boolean,
        private val scheme: String = "Bearer"
) {

    private val log = Logger.getLogger(JwtService::class.java)

    companion object {
        private const val JWT_PRINCIPAL_KEY = "jwtPrincipal"
        private const val JWT_PRINCIPAL_CLASS_KEY = "jwtPrincipalClass"
    }

    fun createToken(jwtPrincipal: JwtPrincipal): String {
        //序列化
        jwtPrincipal.eraseCredentials()
        val json = JsonSerializer.serialize(jwtPrincipal)
        val map = mapOf(
                JWT_PRINCIPAL_KEY to json,
                JWT_PRINCIPAL_CLASS_KEY to jwtPrincipal::class.java.name
        )
        return generateWithMap(jwtPrincipal.subject, map)
    }

    fun parse(token: String): JwtPrincipal {
        val claims = getPayload(token)
        val className = claims.get(JWT_PRINCIPAL_CLASS_KEY, String::class.java)
        val clazz = Class.forName(className)
                .asSubclass(JwtPrincipal::class.java)
        val json = claims.get(JWT_PRINCIPAL_KEY, String::class.java)
        return JsonSerializer.deserialize(json, clazz)
    }

    fun refreshToken(token: String): String {
        val claims = getPayload(token)
        return generateWithMap(claims.subject, claims)
    }

    private fun generateWithMap(subject: String, content: Map<String, Any>? = null): String {
        val timestamp = System.currentTimeMillis()
        val issuerAt = Date(timestamp)
        val exp = Date(timestamp + expiration * 1000)
        val claims = Jwts.claims()
        //自定义token内容，注意refresh token方法目前依赖于这行代码，并且其顺序必须在put其他字段之前
        if (content != null) claims.putAll(content)
        claims.setSubject(subject)
                .setAudience(audience)
                .setIssuer(issuer).issuedAt = issuerAt
        if (!(ignoreTokenExpiration)) claims.expiration = exp
        val token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact()
        return addScheme(token)
    }

    private fun getPayload(token: String): Claims {
        return try {
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(removeScheme(token))
                    .body
        } catch (e: JwtException) {
            log.debug(e.message, e)
            throw e
        }
    }

    fun getSubject(token: String): String {
        val claims = getPayload(token)
        return claims.subject
    }

    fun isValid(token: String): Boolean? {
        return try {
            getPayload(token)
            true
        } catch (e: JwtException) {
            false
        }

    }

    private fun addScheme(token: String): String {
        return scheme + " " + token
    }

    private fun removeScheme(token: String): String {
        if (!token.startsWith(scheme + " ")) throw JwtException("scheme mismatch")
        return token.substring(scheme.length + 1)
    }

}
