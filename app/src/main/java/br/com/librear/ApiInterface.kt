package br.com.librear

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PATCH
import retrofit2.http.DELETE
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("user/me")
    fun me(): Call<User>

    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @GET("cursos")
    fun fetchCursos(): Call<List<CourseResponse>>

    @GET("cursos/search")
    fun searchCurso(@Query("query") term: String): Call<List<CourseResponse>>

    @GET("cursos/show/{id}")
    fun fetchCurso(@Path("id") id: Int): Call<CourseResponse>

    @GET("cursos/meus_cursos")
    fun meusCursos(): Call<List<CourseResponse>>

    @POST("cursos/subscribe/{id}")
    fun matricular(@Path("id") id: Int): Call<MsgResponse>
}