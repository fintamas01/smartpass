package ro.futuretechapps.smartpass.data.remote.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val message: String,
    val token: String,

    @SerializedName("token_type")
    val tokenType: String,

    val user: UserDto
)