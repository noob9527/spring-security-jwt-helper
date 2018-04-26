package cn.staynoob.springsecurityjwt

import org.apache.log4j.Logger
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * do something just like BasicAuthenticationFilter
 *
 * @see org.springframework.security.web.authentication.www.BasicAuthenticationFilter
 */
open class JwtAuthenticationFilter(
        private val jwtProperties: JwtProperties,
        private val authenticationEntryPoint: AuthenticationEntryPoint,
        private val authenticationManager: AuthenticationManager
) : OncePerRequestFilter() {

    private val log = Logger.getLogger(JwtAuthenticationFilter::class.java)

    override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain
    ) {

        val tokenString = request.getHeader(jwtProperties.headerField)

        // ignore request if token isn't present or token scheme mismatch
        if (tokenString == null || !tokenString.startsWith(jwtProperties.scheme)) {
            filterChain.doFilter(request, response)
            return
        }

        if (log.isDebugEnabled) {
            log.debug("Jwt Authentication Authorization header was found")
        }

        try {
            val authResult = authenticationManager
                    .authenticate(JwtAuthentication(tokenString))

            if (log.isDebugEnabled) {
                log.debug("Authentication success: $authResult")
            }

            SecurityContextHolder.getContext().authentication = authResult

            onSuccessfulAuthentication(request, response, authResult);

        } catch (e: AuthenticationException) {
            SecurityContextHolder.clearContext()

            if (log.isDebugEnabled) {
                log.debug("Authentication request for failed: $e")
            }

            onUnsuccessfulAuthentication(request, response, e);
            authenticationEntryPoint.commence(
                    request,
                    response,
                    e
            )
            return
        }

        filterChain.doFilter(request, response)
    }

    @Throws(IOException::class)
    open fun onSuccessfulAuthentication(
            request: HttpServletRequest,
            response: HttpServletResponse,
            authResult: Authentication
    ) {
    }

    @Throws(IOException::class)
    open fun onUnsuccessfulAuthentication(
            request: HttpServletRequest,
            response: HttpServletResponse,
            failed: AuthenticationException) {
    }
}