package cn.staynoob.springsecurityjwt.autoconfigure

import cn.staynoob.springsecurityjwt.DefaultJwtPrincipalService
import cn.staynoob.springsecurityjwt.JwtAuthenticationFilter
import cn.staynoob.springsecurityjwt.JwtAuthenticationProvider
import cn.staynoob.springsecurityjwt.JwtPrincipalService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
abstract class JwtWebSecurityConfigurerAdapter : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        super.configure(auth)
        auth.authenticationProvider(jwtAuthenticationProvider())
    }

    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity.addFilterBefore(
                jwtAuthenticationTokenFilter(),
                UsernamePasswordAuthenticationFilter::class.java
        )
    }

    @Bean
    fun jwtAuthenticationProvider(): JwtAuthenticationProvider {
        return JwtAuthenticationProvider()
    }

    @Bean
    fun jwtAuthenticationTokenFilter(): JwtAuthenticationFilter {
        return JwtAuthenticationFilter()
    }

    @Bean
    @ConditionalOnMissingBean(JwtPrincipalService::class)
    fun jwtPrincipalService(): JwtPrincipalService {
        return DefaultJwtPrincipalService()
    }

    /**
     * @see org.springframework.boot.autoconfigure.security.AuthenticationManagerConfiguration.DefaultInMemoryUserDetailsManagerConfigurer#configure
     */
    @Configuration
    class JwtAuthenticationProviderConfig : GlobalAuthenticationConfigurerAdapter() {
        @Autowired
        private lateinit var jwtAuthenticationTokenProvider: JwtAuthenticationProvider

        override fun init(auth: AuthenticationManagerBuilder) {
            super.init(auth)
            auth.authenticationProvider(jwtAuthenticationTokenProvider)
        }
    }

}
