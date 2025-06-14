package br.com.librear

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.content.edit

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val logoutButton = findViewById<Button>(R.id.logout)
        logoutButton.setOnClickListener{
            val prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE)
            prefs.edit() { putBoolean("isLoggedIn", false) }
            prefs.edit() {remove("token")}
            prefs.edit() {remove("name")}
            prefs.edit() {remove("email")}
            prefs.edit() {remove("id")}
            finish()
        }

        val prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val name = findViewById<TextView>(R.id.NomeUsuario)
        name.text = prefs.getString("name", "")


    }
}