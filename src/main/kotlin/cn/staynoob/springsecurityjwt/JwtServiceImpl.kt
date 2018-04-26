package cn.staynoob.springsecurityjwt

import cn.staynoob.springsecurityjwt.exception.SchemeMismatchException
import cn.staynoob.springsecurityjwt.exception.TokenTypeMismatchException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import java.time.Instant
import java.util.*
import javax.annotation.PostConstruct

class JwtServiceImpl(
        private val properties: JwtProperties
) : JwtService {
    companion object {
        val TOKEN_TYPE_KEY = "__type__"
    }

    private lateinit var keyBytes: ByteArray

    @PostConstruct
    fun contextInit() {
        keyBytes = properties.secret!!.toByteArray()
    }

    override fun createAccessToken(claims: JwtClaims): Token {
        return genToken(TokenType.ACCESS_TOKEN, claims)
    }

    override fun createRefreshToken(claims: JwtClaims): Token {
        return genToken(TokenType.REFRESH_TOKEN, claims)
    }

    override fun createAccessToken(refreshToken: String): Token {
        val token = parse(refreshToken)
        if (token.tokenType != TokenType.REFRESH_TOKEN)
            throw TokenTypeMismatchException()
        return genToken(TokenType.ACCESS_TOKEN, token.claims)
    }

    private fun genToken(
            tokenType: TokenType,
            claims: JwtClaims
    ): Token {
        val mutableClaims = Jwts.claims(claims.toMutableMap())
                .apply {
                    val issuedAt = Instant.now()

                    putIfAbsent(Claims.AUDIENCE, properties.audience)
                    putIfAbsent(Claims.ISSUER, properties.issuer)
                    setIssuedAt(Date.from(issuedAt))
                    set(TOKEN_TYPE_KEY, tokenType)
                    if (!contains(Claims.EXPIRATION)) {
                        expiration = if (properties.devMode) {
                            null
                        } else {
                            val duration = tokenType.extractDuration(properties)
                            val exp = issuedAt.plusSeconds(duration)
                            Date.from(exp)
                        }
                    }
                }

        val value = Jwts.builder()
                .setClaims(mutableClaims)
                .signWith(properties.alg, keyBytes)
                .compact()
                .let {
                    addScheme(it)
                }

        return Token(value, tokenType, JwtClaims.fromClaims(mutableClaims))
    }

    override fun parse(token: String): Token {
        val jwt = try {
            removeScheme(token)
        } catch (e: SchemeMismatchException) {
            token
        }
        val claims = Jwts.parser()
                .setSigningKey(keyBytes)
                .parseClaimsJws(jwt)
                .body
        val tokenType = TokenType.valueOf(claims[TOKEN_TYPE_KEY] as String)
        return Token(token, tokenType, JwtClaims.fromClaims(claims))
    }

    private fun addScheme(token: String): String {
        return if (validateScheme(token)) token
        else properties.scheme + token
    }

    override fun removeScheme(token: String): String {
        if (!validateScheme(token))
            throw SchemeMismatchException()
        return token.substring(properties.scheme.length)
    }

    override fun validateScheme(token: String): Boolean {
        return token.startsWith(properties.scheme)
    }

}