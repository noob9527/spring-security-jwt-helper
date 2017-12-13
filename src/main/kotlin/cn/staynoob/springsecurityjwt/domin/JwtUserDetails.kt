package cn.staynoob.springsecurityjwt.domin

import org.springframework.security.core.userdetails.UserDetails

interface JwtUserDetails : UserDetails, JwtPrincipal {
    override val subject: String
        get() = username
}
