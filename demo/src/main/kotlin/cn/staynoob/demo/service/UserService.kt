package cn.staynoob.demo.service

import cn.staynoob.demo.domain.Principal
import cn.staynoob.demo.domain.UserSample
import cn.staynoob.springsecurityjwt.autoconfigure.JwtHelperProperties
import cn.staynoob.springsecurityjwt.domin.JwtPrincipal
import cn.staynoob.springsecurityjwt.domin.JwtUserDetails
import cn.staynoob.springsecurityjwt.service.AbstractJwtPrincipalService
import cn.staynoob.springsecurityjwt.service.JsonWebTokenService
import org.springframework.stereotype.Service

@Service
class UserService(
        jsonWebTokenService: JsonWebTokenService,
        jwtHelperProperties: JwtHelperProperties
) : AbstractJwtPrincipalService(jsonWebTokenService, jwtHelperProperties) {
    override fun parseJwt(token: String): JwtPrincipal {
        return jsonWebTokenService.parse(token, Principal::class)
    }

    override fun loadUser(principal: JwtPrincipal): JwtUserDetails? {
        return UserSample("foo", "bar", listOf())
    }
}