package br.com.librear

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PATCH
import retrofit2.http.DELETE

interface ApiInterface {
    @GET("user/me")
    fun me(): Call<ErrorResponse>

    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}