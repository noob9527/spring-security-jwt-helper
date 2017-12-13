package cn.staynoob.springsecurityjwt.autoconfigure

import cn.staynoob.springsecurityjwt.service.JsonWebTokenService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JwtHelperWebSecurityConfiguration {
    @Bean
    fun jsonWebTokenService(): JsonWebTokenService {
        return JsonWebTokenService()
    }
}