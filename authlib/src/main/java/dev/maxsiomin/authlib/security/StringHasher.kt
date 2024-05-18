package dev.maxsiomin.authlib.security

import java.security.MessageDigest

internal interface StringHasher {

    fun hash(input: String): String

}

class JvmStringHasher : StringHasher {

    override fun hash(input: String): String {
        // Get an instance of the specified algorithm
        val digest = MessageDigest.getInstance(ALGORITHM)
        // Perform the hash
        val hashBytes = digest.digest(input.toByteArray(Charsets.UTF_8))
        // Convert the byte array to a hexadecimal string
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    private companion object {
        const val ALGORITHM = "SHA-256"
    }

}
