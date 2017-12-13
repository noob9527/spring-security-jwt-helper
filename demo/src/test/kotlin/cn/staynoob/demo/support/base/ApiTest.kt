package cn.staynoob.demo.support.base

import cn.staynoob.springsecurityjwt.autoconfigure.JwtHelperAutoConfiguration
import org.springframework.boot.autoconfigure.ImportAutoConfiguration

@ImportAutoConfiguration(JwtHelperAutoConfiguration::class)
abstract class ApiTest
