package cn.staynoob.springsecurityjwt

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

data class JwtAuthentication(
        val tokenString: String,
        val validatedToken: Token? = null
) : Authentication {
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return mutableSetOf()
    }

    override fun getName(): String? {
        return validatedToken?.subject
    }

    override fun getPrincipal(): Any? {
        return validatedToken?.claims ?: tokenString
    }

    override fun getCredentials(): Any? {
        return null
    }

    override fun getDetails(): Any? {
        return validatedToken?.claims
    }

    override fun isAuthenticated(): Boolean {
        return validatedToken != null
    }

    /**
     * do nothing
     */
    override fun setAuthenticated(isAuthenticated: Boolean) {
    }
}