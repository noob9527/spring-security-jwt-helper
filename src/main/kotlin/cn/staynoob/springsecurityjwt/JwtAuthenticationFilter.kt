package cn.staynoob.springsecurityjwt

import cn.staynoob.springsecurityjwt.autoconfigure.JwtHelperProperties
import io.jsonwebtoken.JwtException
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
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
    private lateinit var jwtService: JwtService

    override fun doFilterInternal(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse, filterChain: FilterChain) {
        val token = httpServletRequest.getHeader(jwtHelperProperties.headerField)
        if (token == null) {
            filterChain.doFilter(httpServletRequest, httpServletResponse)
            return
        }

        try {
            val principal = jwtService.parse(token)
            val authToken = JwtAuthenticationToken(principal)
            val auth = authenticationManager.authenticate(authToken)
            SecurityContextHolder.getContext().authentication = auth
        } catch (e: JwtException) {
            SecurityContextHolder.clearContext()
            authenticationEntryPoint.commence(
                    httpServletRequest,
                    httpServletResponse,
                    JwtAuthenticationException(e.message, e)
            )
            return
        } catch (e: AuthenticationException) {
            SecurityContextHolder.clearContext()
            authenticationEntryPoint.commence(
                    httpServletRequest,
                    httpServletResponse,
                    e
            )
            return
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse)
    }


}