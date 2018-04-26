package cn.staynoob.springsecurityjwt

data class TokenPair(
        val accessToken: Token,
        val refreshToken: Token
) {
    val accessTokenExpiration = accessToken.expiration
}
