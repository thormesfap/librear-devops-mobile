package br.com.librear

import android.R
import br.com.librear.customView.StripInterface
import com.google.gson.annotations.SerializedName


data class ErrorResponse(
    val message: String,
    val status : String?,
    val codigo: String?
)

data class User(
    val id: Int,
    val name: String,
    val email: String,
)

data class LoginResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("expires_in")
    val expiresIn: Int,
)

data class CourseResponse(
    val id: Int,
    val titulo: String,
    @SerializedName("capa_url")
    val capaUrl: String?,
    val descricao: String,
    val categoria: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val aulas: List<AulaResponse>?,
    val leituras: List<LeituraResponse>?
)

data class AulaResponse(
    val id: Int,
    override val sequencia: Int,
    override val titulo: String,
    override val duracaoMinutos: Int,
    val videoUrl: String,
    val transcricao: String?,
    @SerializedName("curso_id")
    val cursoId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
): StripInterface

data class LeituraResponse(
    val id: Int,
    override val sequencia: Int,
    override val titulo: String,
    val conteudo: String,
    @SerializedName("curso_id")
    val cursoId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    override val duracaoMinutos: Int? = null
): StripInterface

data class MsgResponse(
    val msg: String
)