package dev.maxsiomin.authlib.security

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class JvmStringHasherTest {

    private val stringHasher = JvmStringHasher()

    @Test
    fun `hash is always the same`() {
        val string = buildString {
            ('a'..'z').random()
        }
        val uniqueStrings = (0..9).map { i ->
            stringHasher.hash(string)
        }.distinct()

        assertThat(uniqueStrings.size).isEqualTo(1)
    }

    @Test
    fun `hash differs for different string`() {
        val result1 = stringHasher.hash("test1")
        val result2 = stringHasher.hash("test2")
        assertThat(result1).isNotEqualTo(result2)
    }

    @Test
    fun `hash is not equal to the original string`() {
        val input = "test"
        val result = stringHasher.hash(input)
        assertThat(result).isNotEqualTo(input)
    }

}