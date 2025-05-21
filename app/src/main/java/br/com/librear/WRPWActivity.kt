package br.com.librear

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WRPWActivity : AppCompatActivity() {
    lateinit var inputEmail: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_wrpw)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.wrpw)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        inputEmail = findViewById<EditText>(R.id.inputEmail)

        val botaoEnviar: Button = findViewById<Button>(R.id.botaoEnviar)
        botaoEnviar.setOnClickListener {
            if(!validateField()){
                Toast.makeText(this, "Preencha todos os campos corretamente", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            Toast.makeText(this, "E-mail para redefinição de senha enviado", Toast.LENGTH_SHORT)
                .show()
        }

        val avancar: TextView = findViewById<TextView>(R.id.linkAvancar)
        avancar.setOnClickListener {
            val intent = Intent(this, PWResetActivity::class.java)
            startActivity(intent)
        }

        val voltar: TextView = findViewById<TextView>(R.id.linkVoltar)
        voltar.setOnClickListener{
            this.finish()
        }

    }
    private fun validateField(): Boolean{
        if(inputEmail.text.isEmpty()){
            inputEmail.error = "Campo obrigatório"
            return false
        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(inputEmail.text).matches()){
            inputEmail.error = "E-mail inválido"
            return false
        }
        return true
    }
}