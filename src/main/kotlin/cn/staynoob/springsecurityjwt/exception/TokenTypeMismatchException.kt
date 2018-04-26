package cn.staynoob.springsecurityjwt.exception

import io.jsonwebtoken.JwtException

class TokenTypeMismatchException: JwtException("token type mismatch")
