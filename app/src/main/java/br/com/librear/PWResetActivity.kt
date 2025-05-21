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

class PWResetActivity : AppCompatActivity() {
    private lateinit var inputSenha: EditText
    private lateinit var inputConfirmaSenha: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pwreset)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pwreset)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        inputSenha = findViewById<EditText>(R.id.inputSenha)
        inputConfirmaSenha = findViewById<EditText>(R.id.inputRepetirSenha)

        val voltar: TextView = findViewById<TextView>(R.id.linkVoltar)
        voltar.setOnClickListener {
            this.finish()
        }

        val botaoEnviar: Button = findViewById<Button>(R.id.botaoEnviar)
        botaoEnviar.setOnClickListener {
            if(!validateFields()){
                Toast.makeText(this, "Preencha os campos corretamente", Toast.LENGTH_SHORT)
                .show()
                return@setOnClickListener
            }
            Toast.makeText(this, "Senha alterada com sucesso", Toast.LENGTH_SHORT)
                .show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
    private fun validateFields(): Boolean {
        if(inputSenha.text.isEmpty()){
            inputSenha.error = "Campo obrigatório"
            return false
        }
        if(inputConfirmaSenha.text.isEmpty()){
            inputConfirmaSenha.error = "Campo obrigatório"
            return false
        }
        if(inputSenha.text.length < 8){
            inputSenha.error = "Mínimo de 8 caracteres"
            return false
        }
        if(inputSenha.text.toString() != inputConfirmaSenha.text.toString()){
            inputConfirmaSenha.error = "Senhas não conferem"
            return false
        }
        return true
    }


}