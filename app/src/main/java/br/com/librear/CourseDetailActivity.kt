package br.com.librear


import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import br.com.librear.customView.ActivityStrip
import br.com.librear.customView.CourseDetailCard
import br.com.librear.databinding.ActivityCourseDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CourseDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCourseDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        val courseId = intent.getIntExtra("courseId", -1)


        if (courseId > -1) {
            fetchCourseDetails(courseId)
        }
        setupWindowInsets()
        setupListeners()
    }

    private fun fetchCourseDetails(courseId: Int) {
        RetrofitInstance.apiInterface.fetchCurso(courseId).enqueue(object :
            Callback<CourseResponse> {
            override fun onResponse(
                call: Call<CourseResponse>,
                response: Response<CourseResponse>
            ) {
                val course = response.body()
                if (course == null) {
                    Toast.makeText(
                        this@CourseDetailActivity,
                        "Erro ao buscar dados do curso!",
                        Toast.LENGTH_SHORT).show()
                    return
                }
                populateCourseDetailCard(course)
                populateAulas(course.aulas ?: emptyList())
                populateLeituras(course.leituras ?: emptyList())
            }

            override fun onFailure(call: Call<CourseResponse>, t: Throwable) {
                Toast.makeText(
                    this@CourseDetailActivity,
                    "Erro ao buscar dados do curso!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
    private fun populateCourseDetailCard(course: CourseResponse) {
        val courseDetailCardView = findViewById<CourseDetailCard>(R.id.course_detail_card_id)
        courseDetailCardView.setCourseData(course)
    }

    private fun populateAulas(aulas: List<AulaResponse>) {
        val linearLayout = binding.linearDetail

        val aulasTextView = linearLayout.findViewWithTag<TextView>("aulas_header")
        val aulasIndex = if(aulasTextView != null) linearLayout.indexOfChild(aulasTextView) + 1 else 1
        aulas.forEachIndexed { index, aula ->
            val activityStrip = ActivityStrip(this)
            activityStrip.setStripData(aula)
            linearLayout.addView(activityStrip, aulasIndex + index)
        }
    }

    private fun populateLeituras(leituras: List<LeituraResponse>){
        val linearLayout = binding.linearDetail
        val leiturasTextView = linearLayout.findViewWithTag<TextView>("leituras_header")
        val leiturasIndex = if(leiturasTextView != null) linearLayout.indexOfChild(leiturasTextView) + 1 else 1
        leituras.forEachIndexed { index, leitura ->
            val activityStrip = ActivityStrip(this)
            activityStrip.setStripData(leitura)
            linearLayout.addView(activityStrip, leiturasIndex + index)
        }
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
