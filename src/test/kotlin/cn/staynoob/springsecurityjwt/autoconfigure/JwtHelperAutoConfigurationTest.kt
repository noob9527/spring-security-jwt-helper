package cn.staynoob.springsecurityjwt.autoconfigure

import cn.staynoob.springsecurityjwt.JwtProperties
import cn.staynoob.springsecurityjwt.support.base.TestBase
import io.jsonwebtoken.SignatureAlgorithm
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import kotlin.reflect.full.declaredMemberProperties

@ImportAutoConfiguration(JwtHelperAutoConfiguration::class)
@ContextConfiguration(initializers = [ConfigFileApplicationContextInitializer::class])
@ActiveProfiles("property-test")
class JwtHelperAutoConfigurationTest : TestBase() {

    @Autowired
    private lateinit var properties: JwtProperties

    @Test
    @DisplayName("should set up properties")
    fun propertyTest() {
        val res = setOf(
                "secret",
                "issuer",
                "audience",
                "scheme",
                "headerField"
        ).map { propName ->
            JwtProperties::class
                    .declaredMemberProperties
                    .firstOrNull { it.name == propName }
                    ?.get(properties)
        }
        assertThat(res).hasSize(5)
        assertThat(res).containsOnly("test")
        assertThat(properties.devMode).isTrue()
        assertThat(properties.alg).isEqualTo(SignatureAlgorithm.HS512)
    }
}