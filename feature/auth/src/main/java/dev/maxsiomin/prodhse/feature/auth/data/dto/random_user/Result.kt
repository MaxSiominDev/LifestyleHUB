package dev.maxsiomin.prodhse.feature.auth.data.dto.random_user


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
internal data class Result(
    @SerialName("gender")
    val gender: String,
    @SerialName("name")
    val name: Name,
    @SerialName("location")
    val location: Location,
    @SerialName("email")
    val email: String,
    @SerialName("login")
    val login: Login,
    @SerialName("dob")
    val dob: Dob,
    @SerialName("registered")
    val registered: Registered,
    @SerialName("phone")
    val phone: String,
    @SerialName("cell")
    val cell: String,
    @SerialName("id")
    val id: Id,
    @SerialName("picture")
    val picture: Picture,
    @SerialName("nat")
    val nat: String
)