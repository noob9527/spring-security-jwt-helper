package cn.staynoob.springsecurityjwt.service

import cn.staynoob.springsecurityjwt.autoconfigure.JwtHelperAutoConfiguration
import cn.staynoob.springsecurityjwt.domin.JwtPrincipal
import cn.staynoob.springsecurityjwt.support.base.TestBase
import com.fasterxml.jackson.annotation.JsonIgnore
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.ImportAutoConfiguration

@ImportAutoConfiguration(JwtHelperAutoConfiguration::class)
class JsonWebTokenServiceTest : TestBase() {

    @Autowired
    private lateinit var service: JsonWebTokenService

    data class Sample(
            val foo: String = "foo",
            val bar: String = "bar"
    ) : JwtPrincipal {
        @get:JsonIgnore
        override val subject: String
            get() = foo
    }

    @Test
    @DisplayName("createToken")
    fun createTokenTest() {
        assertThat(service.createToken(Sample()))
                .isNotEmpty()
    }

    @Test
    @DisplayName("Parse")
    fun parse() {
        val sample = Sample()
        val token = service.createToken(sample)
        val result = service.parse(token, Sample::class)
        assertThat(result).isEqualTo(sample)
    }

    @Test
    @DisplayName("RefreshToken")
    fun refreshToken() {
        val sample = Sample()
        val token = service.createToken(sample)
        val refreshToken = service.refreshToken(token)
        val res1 = service.parse(token, Sample::class)
        val res2 = service.parse(refreshToken, Sample::class)
        assertThat(res1).isEqualTo(res2)
    }
}