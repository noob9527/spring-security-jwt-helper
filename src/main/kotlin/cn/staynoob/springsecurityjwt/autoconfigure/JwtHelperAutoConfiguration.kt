package cn.staynoob.springsecurityjwt.autoconfigure

import cn.staynoob.springsecurityjwt.JwtProperties
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
@Import(
        JwtHelperWebSecurityConfiguration::class
)
@AutoConfigureAfter(SecurityAutoConfiguration::class)
class JwtHelperAutoConfiguration
