package dev.maxsiomin.prodhse.feature.auth.data.dto.random_user


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
internal data class Login(
    @SerialName("uuid")
    val uuid: String,
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String,
    @SerialName("salt")
    val salt: String,
    @SerialName("md5")
    val md5: String,
    @SerialName("sha1")
    val sha1: String,
    @SerialName("sha256")
    val sha256: String
)