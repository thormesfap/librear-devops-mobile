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

class PWResetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pwreset)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pwreset)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val voltar: TextView = findViewById<TextView>(R.id.linkVoltar)
        voltar.setOnClickListener {
            val intent = Intent(this, WRPWActivity::class.java)
            startActivity(intent)
        }

        val botaoEnviar: Button = findViewById<Button>(R.id.botaoEnviar)
        botaoEnviar.setOnClickListener {
            Toast.makeText(this, "Senha alterada com sucesso", Toast.LENGTH_SHORT)
                .show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


    }
}