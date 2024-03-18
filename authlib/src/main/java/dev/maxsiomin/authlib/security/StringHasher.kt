package dev.maxsiomin.authlib.security

import java.security.MessageDigest

object StringHasher {

    fun hashString(input: String, algorithm: String = "SHA-256"): String {
        // Get an instance of the specified algorithm
        val digest = MessageDigest.getInstance(algorithm)
        // Perform the hash
        val hashBytes = digest.digest(input.toByteArray(Charsets.UTF_8))
        // Convert the byte array to a hexadecimal string
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

}