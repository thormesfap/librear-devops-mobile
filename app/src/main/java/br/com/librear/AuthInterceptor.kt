package br.com.librear

import android.content.Context
import android.content.Intent
import okhttp3.Interceptor
import okhttp3.Response
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.edit

data class ProtectedRoute(val pattern: Regex, val method: String)

class AuthInterceptor(private val context: Context): Interceptor {
    val authRoutes: List<ProtectedRoute> = listOf(
        ProtectedRoute(Regex("/api/user/me"), "GET"),
        ProtectedRoute(Regex("/api/cursos/create"), "POST"),
        ProtectedRoute(Regex("/api/cursos/update"), "PUT"),
        ProtectedRoute(Regex("/api/cursos/delete"), "DELETE"),
        ProtectedRoute(Regex("/api/cursos/subscribe/\\d+"), "POST"),
        ProtectedRoute(Regex("/api/cursos/meus_cursos"), "GET"),
        ProtectedRoute(Regex("/api/aulas/create"), "POST"),
        ProtectedRoute(Regex("/api/aulas/update"), "PUT"),
        ProtectedRoute(Regex("/api/aulas/delete"), "DELETE"),
        ProtectedRoute(Regex("/api/aulas/\\d+/visto"), "PATCH"),
        ProtectedRoute(Regex("/api/leituras/*"), "POST"),
        ProtectedRoute(Regex("/api/leituras/*"), "DELETE"),
        ProtectedRoute(Regex("/api/leituras/*"), "PUT"),
        ProtectedRoute(Regex("/api/leituras/\\d+"), "GET"),
        ProtectedRoute(Regex("/api/leituras/\\d+/visto"), "PATCH"),

    )


    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = getToken(context)
        val requestBuilder = originalRequest.newBuilder()
        if(token != null && isEndpointProtected(originalRequest.url.encodedPath, originalRequest.method)){
            requestBuilder
                .header("Authorization", "Bearer $token")
        }
        val request = requestBuilder.build()
        val response = chain.proceed(request)
        if(response.code == 401){
            if(originalRequest.url.encodedPath != "/api/login"){
                Handler(Looper.getMainLooper()).post{
                    Toast.makeText(context, "Sessão expirada, faça login novamente", Toast.LENGTH_LONG).show()
                }
                this.cleanToken(context)
                val intent = Intent(context, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(intent)
            }
        }
        return response
    }

    private fun getToken(context: Context): String?{
        val prefs = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return prefs.getString("token", null)
    }

    private fun cleanToken(context: Context){
        val prefs = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        prefs.edit { remove("token") }
    }

    private fun isEndpointProtected(endpoint: String, method: String): Boolean {
        return this.authRoutes.any { protectedRoute ->
            protectedRoute.pattern.matches(endpoint) && protectedRoute.method.equals(method, ignoreCase = true)
        }
    }

}