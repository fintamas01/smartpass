package ro.futuretechapps.smartpass.data.repository

import android.content.Context
import android.os.Build
import ro.futuretechapps.smartpass.data.local.SessionManager
import ro.futuretechapps.smartpass.data.remote.api.ApiClient
import ro.futuretechapps.smartpass.data.remote.model.LoginRequest
import ro.futuretechapps.smartpass.data.remote.model.LoginResponse
import ro.futuretechapps.smartpass.data.remote.model.UserDto

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

    suspend fun getCurrentUser(): UserDto {

        val token =
            sessionManager.getToken()
                ?: throw IllegalStateException(
                    "No stored access token"
                )

        return ApiClient.authApi.me(
            authorization = "Bearer $token"
        ).user
    }

    suspend fun logout() {

        val token =
            sessionManager.getToken()

        try {

            if (token != null) {

                ApiClient.authApi.logout(
                    authorization = "Bearer $token"
                )
            }

        } finally {

            sessionManager.clearToken()
        }
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