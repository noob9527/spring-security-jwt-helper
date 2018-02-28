package cn.staynoob.springsecurityjwt

import cn.staynoob.springsecurityjwt.autoconfigure.JwtHelperAutoConfiguration
import cn.staynoob.springsecurityjwt.support.base.TestBase
import com.fasterxml.jackson.annotation.JsonIgnore
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.ImportAutoConfiguration

@ImportAutoConfiguration(JwtHelperAutoConfiguration::class)
class JwtServiceTest : TestBase() {

    @Autowired
    private lateinit var service: JwtService

    data class Sample(
            val username: String = "username",
            var password: String? = "password"
    ) : JwtPrincipal {
        @get:JsonIgnore
        override val subject: String
            get() = username

        override fun eraseCredentials() {
            password = null
        }
    }

    @Test
    @DisplayName("test100")
    fun test100() {
        println(Sample::class.java.canonicalName)
        println(Sample::class.java.name)
        println(Sample::class.qualifiedName)
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
        println(token)
        val result = service.parse(token)
        println(result)
        assertThat(result).isEqualTo(sample)
    }

    @Test
    @DisplayName("RefreshToken")
    fun refreshToken() {
        val sample = Sample()
        val token = service.createToken(sample)
        val refreshToken = service.refreshToken(token)
        val res1 = service.parse(token)
        val res2 = service.parse(refreshToken)
        assertThat(res1).isEqualTo(res2)
    }
}