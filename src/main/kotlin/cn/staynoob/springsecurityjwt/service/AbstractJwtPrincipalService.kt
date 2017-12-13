package cn.staynoob.springsecurityjwt.service

import cn.staynoob.springsecurityjwt.autoconfigure.JwtHelperProperties

abstract class AbstractJwtPrincipalService(
        protected val jsonWebTokenService: JsonWebTokenService,
        protected val jwtHelperProperties: JwtHelperProperties
) : JwtPrincipalService
