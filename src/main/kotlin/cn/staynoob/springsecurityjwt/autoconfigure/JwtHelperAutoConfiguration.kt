package cn.staynoob.springsecurityjwt.autoconfigure

import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@EnableConfigurationProperties(JwtHelperProperties::class)
@Import(JwtHelperWebSecurityConfiguration::class)
@AutoConfigureAfter(SecurityAutoConfiguration::class)
class JwtHelperAutoConfiguration
