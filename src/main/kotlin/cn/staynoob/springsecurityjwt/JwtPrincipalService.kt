package cn.staynoob.springsecurityjwt

import org.springframework.security.core.GrantedAuthority

interface JwtPrincipalService {
    /**
     * a hook to complete userInfo
     *
     * the argument principal is deserialized from jwt
     * it might not contain the full user info
     * maybe it just has an username(It depends on your implementation details)
     * this method provide a hook that you can load the full user info from Db
     *
     * if you choose to serialize the full user info in a jwt, and you
     * can ensure that the user won't be change in the whole jwt lifecycle, then
     * you can just return the argument, as the [DefaultJwtPrincipalService] do
     *
     * @param principal deserialized from jwt
     * @return principal with full detail
     */
    fun loadPrincipal(principal: JwtPrincipal): JwtPrincipal?

    /**
     * load authorities
     *
     * the returned authorities will be stored in [org.springframework.security.core.Authentication]
     * @param principal returned by the loadPrincipal method
     * @return authorities
     */
    fun loadAuthorities(principal: JwtPrincipal): Collection<GrantedAuthority>?
}