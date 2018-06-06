package cn.staynoob.springsecurityjwt

class JwtStore {
    companion object {
        private val tokenStore: ThreadLocal<Token?> = ThreadLocal()

        fun setToken(token: Token) {
            tokenStore.set(token)
        }

        fun getToken() = tokenStore.get()
    }
}