package cn.staynoob.springsecurityjwt.service

import cn.staynoob.springsecurityjwt.autoconfigure.JwtHelperProperties
import cn.staynoob.springsecurityjwt.domin.JwtPrincipal
import cn.staynoob.springsecurityjwt.domin.JwtUserDetails

class NoopJwtPrincipalService(
        jsonWebTokenService: JsonWebTokenService,
        jwtHelperProperties: JwtHelperProperties
) : AbstractJwtPrincipalService(jsonWebTokenService, jwtHelperProperties) {
    override fun parseJwt(token: String): JwtPrincipal {
        TODO("not implemented")
    }

    override fun loadUser(principal: JwtPrincipal): JwtUserDetails? {
        TODO("not implemented")
    }
}
