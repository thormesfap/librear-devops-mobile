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

class WRPWActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_wrpw)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.wrpw)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val botaoEnviar: Button = findViewById<Button>(R.id.botaoEnviar)
        botaoEnviar.setOnClickListener {
            Toast.makeText(this, "E-mail para redefinição de senha enviado", Toast.LENGTH_SHORT)
                .show()
        }

        val avancar: TextView = findViewById<TextView>(R.id.linkAvancar)
        avancar.setOnClickListener {
            val intent = Intent(this, PWResetActivity::class.java)
            startActivity(intent)
        }

    }
}