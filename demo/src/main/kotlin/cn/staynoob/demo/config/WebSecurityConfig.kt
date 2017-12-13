package cn.staynoob.demo.config

import cn.staynoob.springsecurityjwt.autoconfigure.JwtWebSecurityConfigurerAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler

/**
 * Created by xy on 16-10-6.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig : JwtWebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(httpSecurity: HttpSecurity) {
        super.configure(httpSecurity)
        httpSecurity
                //enable cors
                .cors().and()
                //don't need CSRF because the token is invulnerable
                .csrf().disable()
                //handle the unauthorized exception
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                //handle the access denied exception
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                //don't create session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                //start request authorize config
                .authorizeRequests()
                //permitAll
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/test"
                ).permitAll()
                .antMatchers("/**/authenticate/**").permitAll()
                .anyRequest().authenticated()
    }

    @Bean
    fun authenticationEntryPoint(): AuthenticationEntryPoint {
        return AuthenticationEntryPoint { _, httpServletResponse, e ->
            // This is invoked when user tries to access a secured REST resource without supplying any credentials
            // We should just send a 401 Unauthorized response because there is no 'login page' to redirect to
            httpServletResponse.contentType = "application/json"
//            httpServletResponse.status = HttpServletResponse.SC_UNAUTHORIZED
            httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), e.message)
        }
    }

    @Bean
    fun accessDeniedHandler(): AccessDeniedHandler {
        return AccessDeniedHandler { _, httpServletResponse, _ ->
            httpServletResponse.contentType = "application/json"
//            httpServletResponse.status = HttpServletResponse.SC_FORBIDDEN
            httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value())
        }
    }
}
