package br.com.librear

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.librear.customView.Header

class MainActivity : AppCompatActivity(), Header.OnProfileClickListener, Header.OnSearchListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.form)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val header = findViewById<Header>(R.id.header_main)
        header.setOnProfileClickListener(this)
        header.setOnSearchListener(this)
    }

    override fun onProfileClick() {
        println("Botão de perfil clicado.")
    }

    override fun onSearchSubmit(searchText: String) {
        val intent = Intent(this, SearchResultActivity::class.java)
        intent.putExtra("searchText", searchText)
        startActivity(intent)
    }
}