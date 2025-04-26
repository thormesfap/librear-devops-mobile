package br.com.librear

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AulaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aula)

        val botaoConcluido = findViewById<Button>(R.id.botaoConcluido)


        botaoConcluido.setOnClickListener {
            Toast.makeText(this, "Aula concluída", Toast.LENGTH_SHORT).show()
        }
    }
}
