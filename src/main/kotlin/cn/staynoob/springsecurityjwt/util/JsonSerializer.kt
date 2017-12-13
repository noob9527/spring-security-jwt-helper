package cn.staynoob.springsecurityjwt.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import kotlin.reflect.KClass

/**
 * Created by xy on 17-1-9.
 */
object JsonSerializer {

    val objectMapper = ObjectMapper()
            .registerKotlinModule()

    fun serialize(obj: Any): String {
        return objectMapper.writeValueAsString(obj)
    }

    fun <T : Any> deserialize(jsonStr: String, clazz: KClass<T>): T {
        return objectMapper.readValue(jsonStr, clazz.java)
    }

    inline fun <reified T : Any> deserialize(jsonStr: String): T {
        return objectMapper.readValue(jsonStr)
    }
}
