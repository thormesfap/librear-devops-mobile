package br.com.librear

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//URL Heroku Thormes: https://pi-devops-backend-644e3c3f5916.herokuapp.com/api/
//URL Heroku Projeto: https://librear-api-f0e82c02ea90.herokuapp.com/api/
//URL Backend localhost: http://10.0.2.2:8080/api/
object RetrofitInstance {
    private lateinit var retrofit: Retrofit
    lateinit var apiInterface: ApiInterface
        private set

    private fun createOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context.applicationContext))
            .build()
    }

    fun initialize(context: Context){
        if(::retrofit.isInitialized){
            return
        }
        retrofit = Retrofit.Builder().baseUrl("https://pi-devops-backend-644e3c3f5916.herokuapp.com/api/")
            .client(createOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiInterface = retrofit.create(ApiInterface::class.java)
    }
}