package cn.staynoob.springsecurityjwt

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

abstract class AbstractJwtWebSecurityConfigurerAdapter : WebSecurityConfigurerAdapter() {

    @Autowired
    protected lateinit var jwtProperties: JwtProperties

    @Autowired
    protected lateinit var jwtAuthenticationProvider: DefaultJwtAuthenticationProvider

    @Autowired
    protected lateinit var authenticationEntryPoint: AuthenticationEntryPoint

    override fun configure(auth: AuthenticationManagerBuilder) {
        super.configure(auth)
        auth.authenticationProvider(jwtAuthenticationProvider)
    }

    @Throws(Exception::class)
    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity
                //don't need CSRF
                .csrf().disable()
                //don't create session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //handle the unauthorized exception
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .addFilterBefore(
                        jwtAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter::class.java
                )
    }

    open fun jwtAuthenticationFilter(): JwtAuthenticationFilter {
        return JwtAuthenticationFilter(
                jwtProperties,
                authenticationEntryPoint,
                authenticationManager()
        )
    }

}
