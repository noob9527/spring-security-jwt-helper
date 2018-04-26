package cn.staynoob.springsecurityjwt

import cn.staynoob.springsecurityjwt.kotlin.KotlinAllOpen
import io.jsonwebtoken.SignatureAlgorithm
import org.hibernate.validator.constraints.NotEmpty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotNull

@Validated
@KotlinAllOpen
@ConfigurationProperties(prefix = "spring.security.jwt.helper")
class JwtProperties(
        @get: NotNull
        @get: NotEmpty
        var secret: String? = null,
        var issuer: String? = null,
        var audience: String? = null,
        // unit second
        var accessTokenExpiration: Long = 3600,
        var refreshTokenExpiration: Long = 86400 * 365 * 10,
        var devMode: Boolean = false,
        var alg: SignatureAlgorithm = SignatureAlgorithm.HS256,
        // according to RFC6750 https://tools.ietf.org/html/rfc6750#section-6.1.1
        var scheme: String = "Bearer ",
        // http header
        var headerField: String = "Authorization"
) {
    override fun toString(): String {
        return "JwtProperties(secret=$secret, issuer=$issuer, audience=$audience, accessTokenExpiration=$accessTokenExpiration, refreshTokenExpiration=$refreshTokenExpiration, devMode=$devMode, alg=$alg, scheme='$scheme', headerField='$headerField')"
    }
}

