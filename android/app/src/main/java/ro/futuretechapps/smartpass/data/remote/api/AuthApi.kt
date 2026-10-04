package ro.futuretechapps.smartpass.data.remote.api

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import ro.futuretechapps.smartpass.data.remote.model.LoginRequest
import ro.futuretechapps.smartpass.data.remote.model.LoginResponse

interface AuthApi {

    @Headers(
        "Accept: application/json"
    )
    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse
}