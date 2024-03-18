package dev.maxsiomin.authlib

import dev.maxsiomin.authlib.security.StringHasher
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class StringHasherTest {

    @Test
    fun `string is hashed correctly`() {
        val input = "hello, world"
        val output = StringHasher.hashString(input)
        assertEquals("09ca7e4eaa6e8ae9c7d261167129184883644d07dfba7cbfbc4c8a2e08360d5b", output)
    }

}