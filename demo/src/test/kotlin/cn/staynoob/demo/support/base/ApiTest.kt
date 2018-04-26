package cn.staynoob.demo.support.base

import cn.staynoob.demo.config.WebSecurityConfig
import cn.staynoob.springsecurityjwt.autoconfigure.JwtHelperAutoConfiguration
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.context.annotation.Import

@ImportAutoConfiguration(JwtHelperAutoConfiguration::class)
@Import(WebSecurityConfig::class)
abstract class ApiTest : TestBase()
