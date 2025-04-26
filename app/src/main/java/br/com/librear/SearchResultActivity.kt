package br.com.librear

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.librear.customView.CourseDetailCard
import br.com.librear.customView.Header

class SearchResultActivity : AppCompatActivity(), Header.OnProfileClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search_result)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val searchText = intent.getStringExtra("searchText")
        val textView = findViewById<TextView>(R.id.text_searchResult)
        textView.text = "Resultado da Busca: " + searchText

        val header = findViewById<Header>(R.id.header_main)
        header.setOnProfileClickListener(this)

        val card = findViewById<CourseDetailCard>(R.id.course_aprendendo_libras)
        card.setOnClickListener {
            val intent = Intent(this, CourseDetailActivity::class.java)
            startActivity(intent)
        }

    }
    override fun onProfileClick() {
        println("Profile Clicked")
    }
}