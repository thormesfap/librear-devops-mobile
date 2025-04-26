package br.com.librear

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.View
import android.widget.LinearLayout
import br.com.librear.customView.ActivityStrip

class CourseDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_course_detail)

        setupWindowInsets()
        setupListeners()
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupListeners() {
        val linearLayout = findViewById<LinearLayout>(R.id.linear_detail)

        // Iterate over all children of the LinearLayout
        for (i in 0 until linearLayout.childCount) {
            val childView = linearLayout.getChildAt(i)

            // Check if the child is an instance of ActivityStrip
            if (childView is ActivityStrip) {
                childView.setOnClickListener {
                    val intent = Intent(this, AulaActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}
