package br.com.librear

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val entrar_button: Button = findViewById<Button>(R.id.button_login)
        entrar_button.setOnClickListener {
            val email = findViewById<TextView>(R.id.input_email).text.toString()
            val senha = findViewById<TextView>(R.id.input_senha).text.toString()
            RetrofitInstance.apiInterface.login(LoginRequest(email, senha)).enqueue(
                object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful) {
                            val prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE)
                            prefs.edit().putBoolean("isLoggedIn", true).apply()
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

    }
}