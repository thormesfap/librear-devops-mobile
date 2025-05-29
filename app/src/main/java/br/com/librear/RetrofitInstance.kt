package br.com.librear
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//URL Heroku Thormes: https://pi-devops-backend-644e3c3f5916.herokuapp.com/api/
//URL Heroku Projeto: https://librear-api-f0e82c02ea90.herokuapp.com/api/
//URL Backend localhost: http://10.0.2.2:8080/api/
object RetrofitInstance {
    private val retrofit by lazy{
        Retrofit.Builder().baseUrl("https://10.0.2.2:8080/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiInterface by lazy{
        retrofit.create(ApiInterface::class.java)
    }
}