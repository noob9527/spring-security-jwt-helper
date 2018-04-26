package cn.staynoob.springsecurityjwt

import cn.staynoob.springsecurityjwt.autoconfigure.JwtHelperAutoConfiguration
import cn.staynoob.springsecurityjwt.support.base.TestBase
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer
import org.springframework.test.context.ContextConfiguration

@ImportAutoConfiguration(JwtHelperAutoConfiguration::class)
@ContextConfiguration(initializers = [ConfigFileApplicationContextInitializer::class])
class JwtServiceImplTest : TestBase() {

    @Autowired
    private lateinit var service: JwtServiceImpl

    @Test
    @DisplayName("test100")
    fun test100() {
    }

}