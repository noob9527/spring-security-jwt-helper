package cn.staynoob.springsecurityjwt.exception

import io.jsonwebtoken.JwtException

class SchemeMismatchException : JwtException("scheme mismatch")
