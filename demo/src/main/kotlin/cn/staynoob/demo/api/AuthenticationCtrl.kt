package cn.staynoob.demo.api

import cn.staynoob.springsecurityjwt.JwtClaims
import cn.staynoob.springsecurityjwt.JwtService
import cn.staynoob.springsecurityjwt.TokenPair
import io.jsonwebtoken.Jwts
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/api/authenticate"], produces = [(MediaType.APPLICATION_JSON_UTF8_VALUE)])
@PreAuthorize("permitAll")
class AuthenticationCtrl(
        @Suppress("unused")
        private val authenticationManager: AuthenticationManager,
        private val jwtService: JwtService
) {

    data class Credentials(
            val username: String,
            val password: String
    )

    @PostMapping
    fun create(@Valid @RequestBody credentials: Credentials): TokenPair {
        // your own authenticate logic e.g.:
//        val auth = UsernamePasswordAuthenticationToken(
//                credentials.username,
//                credentials.password
//        ).let { authenticationManager.authenticate(it) }
//        SecurityContextHolder.getContext().authentication = auth
        return jwtService.createTokenPair(
                JwtClaims.of(credentials.username)
        )
    }

    @PutMapping
    fun refresh(@RequestBody refreshToken: String): TokenPair {
        return jwtService.refresh(refreshToken)
    }
}