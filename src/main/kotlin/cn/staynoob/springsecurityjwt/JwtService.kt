package cn.staynoob.springsecurityjwt

import cn.staynoob.springsecurityjwt.exception.SchemeMismatchException
import io.jsonwebtoken.JwtException

/**
 * all the methods related to create or refresh token
 * will add scheme to generated token automatically
 */
interface JwtService {
    fun createAccessToken(claims: JwtClaims): Token

    @Throws(exceptionClasses = [JwtException::class])
    fun createAccessToken(refreshToken: String): Token

    fun createRefreshToken(claims: JwtClaims): Token

    fun createTokenPair(claims: JwtClaims): TokenPair {
        val a = createAccessToken(claims)
        val r = createRefreshToken(claims)
        return TokenPair(a, r)
    }

    @Throws(exceptionClasses = [JwtException::class])
    fun refresh(refreshToken: String): TokenPair {
        val r = parse(refreshToken)
        val a = createAccessToken(refreshToken)
        return TokenPair(a, r)
    }


    @Throws(exceptionClasses = [JwtException::class])
    fun parse(token: String): Token

    @Throws(exceptionClasses = [SchemeMismatchException::class])
    fun removeScheme(token: String): String

    /**
     * indicates if given string is a jwt
     */
    fun validateScheme(token: String): Boolean
}