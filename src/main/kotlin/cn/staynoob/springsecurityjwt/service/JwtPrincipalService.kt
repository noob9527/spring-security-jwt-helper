package cn.staynoob.springsecurityjwt.service

import cn.staynoob.springsecurityjwt.domin.JwtPrincipal
import cn.staynoob.springsecurityjwt.domin.JwtUserDetails

interface JwtPrincipalService {
    fun parseJwt(token: String): JwtPrincipal
    fun loadUser(principal: JwtPrincipal): JwtUserDetails?
}