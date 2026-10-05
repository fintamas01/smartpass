package ro.futuretechapps.smartpass.data.remote.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import ro.futuretechapps.smartpass.data.remote.model.LoginRequest
import ro.futuretechapps.smartpass.data.remote.model.LoginResponse
import ro.futuretechapps.smartpass.data.remote.model.MeResponse
import ro.futuretechapps.smartpass.data.remote.model.MessageResponse

interface AuthApi {

    @Headers(
        "Accept: application/json"
    )
    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @Headers(
        "Accept: application/json"
    )
    @GET("me")
    suspend fun me(
        @Header("Authorization")
        authorization: String
    ): MeResponse

    @Headers(
        "Accept: application/json"
    )
    @POST("logout")
    suspend fun logout(
        @Header("Authorization")
        authorization: String
    ): MessageResponse
}