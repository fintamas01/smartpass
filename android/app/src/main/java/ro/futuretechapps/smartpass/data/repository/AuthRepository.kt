package ro.futuretechapps.smartpass.data.repository

import android.os.Build
import ro.futuretechapps.smartpass.data.remote.api.ApiClient
import ro.futuretechapps.smartpass.data.remote.model.LoginRequest
import ro.futuretechapps.smartpass.data.remote.model.LoginResponse

class AuthRepository {

    suspend fun login(
        email: String,
        password: String
    ): LoginResponse {

        val deviceName =
            "${Build.MANUFACTURER} ${Build.MODEL}"

        return ApiClient.authApi.login(
            LoginRequest(
                email = email,
                password = password,
                deviceName = deviceName
            )
        )
    }
}