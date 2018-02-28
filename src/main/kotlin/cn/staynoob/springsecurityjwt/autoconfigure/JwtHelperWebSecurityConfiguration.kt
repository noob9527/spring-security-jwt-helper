package cn.staynoob.springsecurityjwt.autoconfigure

import cn.staynoob.springsecurityjwt.JwtService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JwtHelperWebSecurityConfiguration {
    @Bean
    fun jwtService(jwtHelperProperties: JwtHelperProperties): JwtService {
        return JwtService(
                jwtHelperProperties.secret,
                jwtHelperProperties.issuer,
                jwtHelperProperties.audience,
                jwtHelperProperties.expiration,
                jwtHelperProperties.ignoreTokenExpiration,
                jwtHelperProperties.scheme
        )
    }
}