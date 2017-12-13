package cn.staynoob.springsecurityjwt

import cn.staynoob.springsecurityjwt.autoconfigure.JwtHelperProperties
import cn.staynoob.springsecurityjwt.service.JwtPrincipalService
import io.jsonwebtoken.JwtException
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter : OncePerRequestFilter() {

    private val log = Logger.getLogger(JwtAuthenticationFilter::class.java)

    @Autowired
    private lateinit var authenticationEntryPoint: AuthenticationEntryPoint
    @Autowired
    private lateinit var authenticationManager: AuthenticationManager
    @Autowired
    private lateinit var jwtHelperProperties: JwtHelperProperties
    @Autowired
    private lateinit var jwtPrincipalService: JwtPrincipalService

    override fun doFilterInternal(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse, filterChain: FilterChain) {
        val token = httpServletRequest.getHeader(jwtHelperProperties.headerField)
        if (token == null) {
            filterChain.doFilter(httpServletRequest, httpServletResponse)
            return
        }

        try {
            val principal = jwtPrincipalService.parseJwt(token)
            val authToken = JwtAuthenticationToken(principal)
            val auth = authenticationManager.authenticate(authToken)
            SecurityContextHolder.getContext().authentication = auth
        } catch (e: JwtException) {
            // todo let customer handle jwt parse exception
            SecurityContextHolder.clearContext()
            authenticationEntryPoint.commence(
                    httpServletRequest,
                    httpServletResponse,
                    BadCredentialsException(e.message)
            )
            return
        } catch (e: Exception) {
            SecurityContextHolder.clearContext()
            // AuthenticationException
            val authenticationException
                    = e as? AuthenticationException
                    ?: BadCredentialsException(e.message)
            authenticationEntryPoint.commence(httpServletRequest,
                    httpServletResponse,
                    authenticationException
            )
            return
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse)
    }


}