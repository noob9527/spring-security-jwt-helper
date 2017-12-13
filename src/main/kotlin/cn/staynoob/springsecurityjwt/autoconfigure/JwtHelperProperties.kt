package cn.staynoob.springsecurityjwt.autoconfigure

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.security.jwt.helper")
data class JwtHelperProperties(
        var secret: String = "default",
        //签发者
        var issuer: String? = "default",
        //接收方
        var audience: String? = "default",
        //Token过期时间(second)
        var expiration: Long = 604800,
        //是否永不过期
        var ignoreTokenExpiration: Boolean = false,
        //according to RFC6750 https://tools.ietf.org/html/rfc6750#section-6.1.1
        var scheme: String = "Bearer",
        //http header
        var headerField: String = "Authorization"
)
