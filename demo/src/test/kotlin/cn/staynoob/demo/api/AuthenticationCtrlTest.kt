package cn.staynoob.demo.api

import cn.staynoob.demo.support.base.ApiTest
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [AuthenticationCtrl::class])
class AuthenticationCtrlTest : ApiTest() {

    @Autowired
    private lateinit var mvc: MockMvc

    private val url = "/api/authenticate"

    @Test
    @DisplayName("create token test")
    fun createTest100() {
        val credentials = AuthenticationCtrl.Credentials(
                "foo", "bar"
        )
        val body = jacksonObjectMapper().writeValueAsString(credentials)
        mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(body))
                .andExpect(status().isOk)
                .andExpect(jsonPath("@.accessToken").isString)
                .andExpect(jsonPath("@.refreshToken").isString)
    }
}