package ro.futuretechapps.smartpass.data.repository

import android.content.Context
import android.os.Build
import ro.futuretechapps.smartpass.data.local.SessionManager
import ro.futuretechapps.smartpass.data.remote.api.ApiClient
import ro.futuretechapps.smartpass.data.remote.model.LoginRequest
import ro.futuretechapps.smartpass.data.remote.model.LoginResponse

class AuthRepository(
    context: Context
) {

    private val sessionManager =
        SessionManager(
            context.applicationContext
        )

    suspend fun login(
        email: String,
        password: String
    ): LoginResponse {

        val deviceName =
            "${Build.MANUFACTURER} ${Build.MODEL}"

        val response =
            ApiClient.authApi.login(
                LoginRequest(
                    email = email,
                    password = password,
                    deviceName = deviceName
                )
            )

        sessionManager.saveToken(
            response.token
        )

        return response
    }

    fun getStoredToken(): String? {
        return sessionManager.getToken()
    }

    fun hasStoredToken(): Boolean {
        return sessionManager.hasToken()
    }

    fun clearStoredToken() {
        sessionManager.clearToken()
    }
}