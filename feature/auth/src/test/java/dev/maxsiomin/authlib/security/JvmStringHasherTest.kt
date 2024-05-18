package dev.maxsiomin.authlib.security

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

        assert(uniqueStrings.size == 1)
    }

    @Test
    fun `hash differs for different string`() {
        val result1 = stringHasher.hash("test1")
        val result2 = stringHasher.hash("test2")
        assert(result1 != result2)
    }

    @Test
    fun `hash is not equal to the original string`() {
        assert("test" != stringHasher.hash("test"))
    }

}