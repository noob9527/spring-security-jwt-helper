package cn.staynoob.demo.domain

import cn.staynoob.springsecurityjwt.domin.JwtUserDetails
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class UserSample(
        username: String,
        password: String?,
        authorities: Collection<GrantedAuthority> = emptySet()
) : User(username, password, authorities), JwtUserDetails
