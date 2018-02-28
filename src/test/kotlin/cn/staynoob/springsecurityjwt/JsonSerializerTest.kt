package cn.staynoob.springsecurityjwt

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.json.JsonContent
import org.springframework.boot.test.json.JsonContentAssert

class JsonSerializerTest {

    data class Sample(
            val foo: String? = null,
            val bar: String? = null
    )

    @Test
    @DisplayName("serialize")
    fun serializeTest100() {
        val json = JsonSerializer.serialize(Sample("foo", "bar"))
        val jsonContent = JsonContent<Sample>(Sample::class.java, null, json)
        assertThat<JsonContentAssert>(jsonContent)
                .extractingJsonPathStringValue("foo").isEqualTo("foo")
        assertThat<JsonContentAssert>(jsonContent)
                .extractingJsonPathStringValue("bar").isEqualTo("bar")
    }

    @Test
    @DisplayName("deserialize")
    fun deserializeTest100() {
        val jsonStr = """{"foo":"foo","bar":"bar"}"""
        val result = JsonSerializer.deserialize(jsonStr, Sample::class)
        assertThat(result).isEqualTo(Sample("foo", "bar"))
    }

    @Test
    @DisplayName("inline reified deSerialize")
    fun deserializeTest200() {
        val jsonStr = """{"foo":"foo","bar":"bar"}"""
        val result = JsonSerializer.deserialize<Sample>(jsonStr)
        assertThat(result).isEqualTo(Sample("foo", "bar"))
    }

    data class Comment(
            val content: String,
            var post: Post? = null
    )

    data class Post(
            val title: String
    ) {
        var comments: MutableList<Comment> = mutableListOf()
            set(value) {
                field = value
                field.forEach { it.post = this }
            }
    }

    @Test
    @DisplayName("deserialize complex object test")
    fun deserializeTest300() {
        val json = """
        {
            "title": "foo",
            "comments": [
                {
                    "content": "foo"
                }
            ]
        }
        """.trimIndent()
        val result = JsonSerializer.deserialize<Post>(json)
        assertThat(result.title).isEqualTo("foo")
        assertThat(result.comments).hasSize(1)
        assertThat(result.comments[0].content).isEqualTo("foo")
        assertThat(result.comments[0].post).isEqualTo(result)
    }
}