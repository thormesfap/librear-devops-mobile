package br.com.librear

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignupActivity : AppCompatActivity() {
    lateinit var inputEmail: EditText
    lateinit var inputSenha: EditText
    lateinit var inputSenhaConfirm: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        inputEmail = findViewById(R.id.input_email)
        inputSenha = findViewById(R.id.input_senha)
        inputSenhaConfirm = findViewById(R.id.input_senha_confirm)

        val signup_button: Button = findViewById<Button>(R.id.button_signup)
        signup_button.setOnClickListener {
            if(!validateFields()){
                Toast.makeText(
                    this@SignupActivity,
                    "Corrija os dados do formulário",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            val prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE)
            prefs.edit().putBoolean("isLoggedIn", true).apply()
            Toast.makeText(
                this@SignupActivity,
                "Cadastro bem sucedido!",
                Toast.LENGTH_SHORT
            ).show()

            // Isso é mockado, remover depois e descomentar o codigo a seguir
            val intent = Intent(this@SignupActivity, MainActivity::class.java)
            startActivity(intent)
            return@setOnClickListener

            // Descomentar o codigo a seguir depois
//            val email = inputEmail.text.toString()
//            val senha = inputSenha.text.toString()
//
//            RetrofitInstance.apiInterface.login(LoginRequest(email, senha)).enqueue(
//                object : Callback<LoginResponse> {
//                    override fun onResponse(
//                        call: Call<LoginResponse>,
//                        response: Response<LoginResponse>
//                    ) {
//                        if (response.isSuccessful) {
//                            val prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE)
//                            prefs.edit().putBoolean("isLoggedIn", true).apply()
//                            Toast.makeText(
//                                this@LoginActivity,
//                                "Login bem sucedido!",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
//                            startActivity(intent)
//                        } else {
//                            Toast.makeText(this@LoginActivity, "Login falhou!", Toast.LENGTH_SHORT)
//                                .show()
//                        }
//                    }
//
//                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                        Toast.makeText(this@LoginActivity, "Falha na conexão!", Toast.LENGTH_SHORT)
//                            .show()
//                    }
//
//                }
//            )
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

        if(this.inputSenhaConfirm.text.isEmpty()){
            this.inputSenhaConfirm.error = "Campo obrigatório"
            return false
        }

        if(this.inputSenhaConfirm.text.toString() != this.inputSenha.text.toString()){
            this.inputSenhaConfirm.error = "As senhas não conferem"
            this.inputSenha.error = "As senhas não conferem"
            return false
        }
        return true
    }
}