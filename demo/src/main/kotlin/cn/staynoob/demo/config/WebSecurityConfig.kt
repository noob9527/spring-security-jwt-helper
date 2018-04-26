package cn.staynoob.demo.config

import cn.staynoob.springsecurityjwt.AbstractJwtWebSecurityConfigurerAdapter
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

/**
 * Created by xy on 16-10-6.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig : AbstractJwtWebSecurityConfigurerAdapter()
