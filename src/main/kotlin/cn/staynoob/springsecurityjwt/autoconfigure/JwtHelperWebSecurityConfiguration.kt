package cn.staynoob.springsecurityjwt.autoconfigure

import cn.staynoob.springsecurityjwt.DefaultJwtAuthenticationProvider
import cn.staynoob.springsecurityjwt.JwtProperties
import cn.staynoob.springsecurityjwt.JwtService
import cn.staynoob.springsecurityjwt.JwtServiceImpl
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.web.AuthenticationEntryPoint

@Configuration
class JwtHelperWebSecurityConfiguration {
    @Bean
    @ConditionalOnMissingBean
    fun jwtService(jwtProperties: JwtProperties): JwtService {
        return JwtServiceImpl(jwtProperties)
    }

    @Bean
    @ConditionalOnMissingBean
    fun jwtAuthenticationProvider(): DefaultJwtAuthenticationProvider {
        return DefaultJwtAuthenticationProvider()
    }

    @Bean
    @ConditionalOnMissingBean
    fun jwtAuthenticationEntryPoint(): AuthenticationEntryPoint {
        return AuthenticationEntryPoint { _, httpServletResponse, e ->
            // This is invoked when user tries to access a secured REST resource without supplying any credentials
            // We should just send a 401 Unauthorized response because there is no 'login page' to redirect to
            httpServletResponse.contentType = "application/json"
            httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), e.message)
        }
    }
}