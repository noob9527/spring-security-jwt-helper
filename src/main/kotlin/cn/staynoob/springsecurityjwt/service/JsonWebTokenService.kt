package cn.staynoob.springsecurityjwt.service

import cn.staynoob.springsecurityjwt.autoconfigure.JwtHelperProperties
import cn.staynoob.springsecurityjwt.domin.JwtPrincipal
import cn.staynoob.springsecurityjwt.util.JsonSerializer
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*
import kotlin.reflect.KClass

/**
 * Created by xy on 16-10-7.
 */
@Component
class JsonWebTokenService {

    @Autowired
    private lateinit var jwtHelperProperties: JwtHelperProperties

    private val log = Logger.getLogger(JsonWebTokenService::class.java)

    companion object {
        private const val JWT_PRINCIPAL_KEY = "jwtPrincipal"
    }

    fun createToken(jwtPrincipal: JwtPrincipal): String {
        //序列化
        val json = JsonSerializer.serialize(jwtPrincipal)
        val map = mapOf(JWT_PRINCIPAL_KEY to json)
        return generateWithMap(jwtPrincipal.subject, map)
    }

    fun <T : JwtPrincipal> parse(token: String, clazz: KClass<T>): T {
        val claims = getPayload(token)
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
        val exp = Date(timestamp + jwtHelperProperties.expiration * 1000)
        val claims = Jwts.claims()
        //自定义token内容，注意refresh token方法目前依赖于这行代码，并且其顺序必须在put其他字段之前
        if (content != null) claims.putAll(content)
        claims.setSubject(subject)
                .setAudience(jwtHelperProperties.audience)
                .setIssuer(jwtHelperProperties.issuer).issuedAt = issuerAt
        if (!(jwtHelperProperties.ignoreTokenExpiration)) claims.expiration = exp
        val token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, jwtHelperProperties.secret)
                .compact()
        return addScheme(token)
    }

    private fun getPayload(token: String): Claims {
        return try {
            Jwts.parser()
                    .setSigningKey(jwtHelperProperties.secret)
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
        return jwtHelperProperties.scheme + " " + token
    }

    private fun removeScheme(token: String): String {
        if (!token.startsWith(jwtHelperProperties.scheme + " ")) throw JwtException("scheme mismatch")
        return token.substring(jwtHelperProperties.scheme.length + 1)
    }

}
