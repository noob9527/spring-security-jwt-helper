package cn.staynoob.springsecurityjwt

enum class TokenType {
    ACCESS_TOKEN, REFRESH_TOKEN;

    internal fun extractDuration(properties: JwtProperties): Long {
        return when (this) {
            ACCESS_TOKEN -> properties.accessTokenExpiration
            REFRESH_TOKEN -> properties.refreshTokenExpiration
        }
    }

}

