package br.com.librear
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy{
        Retrofit.Builder().baseUrl("https://librear-api-f0e82c02ea90.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiInterface by lazy{
        retrofit.create(ApiInterface::class.java)
    }
}