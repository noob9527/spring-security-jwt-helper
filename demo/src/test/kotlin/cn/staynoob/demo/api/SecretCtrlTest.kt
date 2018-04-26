package cn.staynoob.demo.api

import cn.staynoob.demo.support.base.ApiTest
import cn.staynoob.springsecurityjwt.JwtClaims
import cn.staynoob.springsecurityjwt.JwtProperties
import cn.staynoob.springsecurityjwt.JwtService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [SecretCtrl::class])
class SecretCtrlTest : ApiTest() {
    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var jwtProperties: JwtProperties

    @Autowired
    private lateinit var jwtService: JwtService

    private val url = "/api/secret"

    @Test
    @DisplayName("should return ok")
    fun test100() {
        val accessToken = jwtService.createAccessToken(JwtClaims.of("foo"))

        mvc.perform(
                get(url)
                        .header(jwtProperties.headerField, accessToken.value)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isOk)
    }

    @Test
    @DisplayName("invalid token should return 401")
    fun test200() {
        mvc.perform(
                get(url)
                        .header(jwtProperties.headerField, "whatever")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isUnauthorized)
    }
}