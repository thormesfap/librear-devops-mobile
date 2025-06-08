package br.com.librear

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.content.edit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    lateinit var inputEmail: EditText
    lateinit var inputSenha: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        inputEmail = findViewById(R.id.input_email)
        inputSenha = findViewById(R.id.input_senha)

        val entrar_button: Button = findViewById<Button>(R.id.button_login)
        entrar_button.setOnClickListener {
            if(!validateFields()){
                Toast.makeText(
                    this@LoginActivity,
                    "Corrija os dados do formulário",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            val email = inputEmail.text.toString()
            val senha = inputSenha.text.toString()

            RetrofitInstance.apiInterface.login(LoginRequest(email, senha)).enqueue(
                object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful) {
                            val prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE)
                            prefs.edit { putString("token", response.body()?.accessToken) }

                            RetrofitInstance.apiInterface.me().enqueue(
                                object : Callback<User>{
                                    override fun onResponse(
                                        call: Call<User?>,
                                        response: Response<User?>
                                    ) {
                                        if(response.isSuccessful){
                                            val prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE)
                                            prefs.edit { putBoolean("isLoggedIn", true) }
                                            prefs.edit { putString("name", response.body()?.name) }
                                            prefs.edit { putString("email", response.body()?.email) }
                                            prefs.edit { putInt("id", response.body()?.id!!) }
                                        }
                                        else{
                                            Toast.makeText(
                                                this@LoginActivity,
                                                "Não foi possível localizar dados do usuário",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            return
                                        }
                                    }

                                    override fun onFailure(call: Call<User?>, t: Throwable) {
                                        return
                                    }
                                }
                            )
                            RetrofitInstance.apiInterface.meusCursos().enqueue(
                                object : Callback<List<CourseResponse>>{
                                    override fun onResponse(
                                        call: Call<List<CourseResponse>?>,
                                        response: Response<List<CourseResponse>?>
                                    ) {
                                        val prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE)
                                        prefs.edit { putStringSet("meusCursos", response.body()?.map { it.id.toString() }?.toSet())}
                                    }

                                    override fun onFailure(
                                        call: Call<List<CourseResponse>?>,
                                        t: Throwable
                                    ) {
                                        TODO("Not yet implemented")
                                    }
                                }
                            )

                            Toast.makeText(
                                this@LoginActivity,
                                "Login bem sucedido!",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@LoginActivity, "Login falhou!", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, "Falha na conexão!", Toast.LENGTH_SHORT)
                            .show()
                    }

                }
            )
        }

        val esqueci_senha: TextView = findViewById<TextView>(R.id.esqueci_senha)
        esqueci_senha.setOnClickListener {
            val intent = Intent(this, WRPWActivity::class.java)
            startActivity(intent)
        }

        val logo = findViewById<ImageView>(R.id.logo)
        logo.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun validateFields(): Boolean {
        if(this.inputEmail.text.isEmpty()){
            this.inputEmail.error = "Campo obrigatório"
            return false
        }
        val email = inputEmail.text
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            this.inputEmail.error = "E-mail inválido"
            return false
        }

        if(this.inputSenha.text.isEmpty()){
            this.inputSenha.error = "Campo obrigatório"
            return false
        }
        if(this.inputSenha.text.length < 8){
            this.inputSenha.error = "A senha deve ter no mínimo 8 caracteres"
            return false
        }
        return true
    }
}