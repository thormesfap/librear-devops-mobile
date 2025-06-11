package br.com.librear

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.librear.customView.CourseDetailCard
import br.com.librear.customView.Header
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchResultActivity : BaseActivity(), Header.OnProfileClickListener{
    private lateinit var coursesRecyclerView: RecyclerView
    private lateinit var courseAdapter: CourseAdapter
    private lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search_result)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupNavigationDrawer(R.id.drawer_layout, R.id.header_main, R.id.included_nav_view)

        val searchText = intent.getStringExtra("searchText")
        val textView = findViewById<TextView>(R.id.text_searchResult)
        textView.text = "Resultado da Busca: " + searchText

        val header = findViewById<Header>(R.id.header_main)
        header.setOnProfileClickListener(this)
        progress = findViewById<ProgressBar>(R.id.progressBar)
        progress.visibility = View.GONE

        coursesRecyclerView = findViewById(R.id.recyclerSearchResult)
        coursesRecyclerView.layoutManager = LinearLayoutManager(this)

        courseAdapter = CourseAdapter(emptyList()) { selectedCourse ->
            val intent = Intent(this, CourseDetailActivity::class.java)
            intent.putExtra("courseId", selectedCourse.id)
            startActivity(intent)
        }
        coursesRecyclerView.adapter = courseAdapter
        handleIntent(intent)
    }

    private fun fetchCourses(term: String) {
        progress.visibility = View.VISIBLE

        val call: Call<List<CourseResponse>>
        if(!term.isEmpty()){
            call = RetrofitInstance.apiInterface.searchCurso(term)
        } else{
            call = RetrofitInstance.apiInterface.fetchCursos()
        }
        call.enqueue(
            object : Callback<List<CourseResponse>> {
                override fun onResponse(
                    call: Call<List<CourseResponse>?>,
                    response: Response<List<CourseResponse>?>
                ) {
                    progress.visibility = View.GONE
                    if (response.isSuccessful) {
                        courseAdapter.updateCourses(response.body() ?: emptyList())
                    } else{
                        Toast.makeText(
                            this@SearchResultActivity,
                            "Erro ${response.code()} ao buscar cursos!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<List<CourseResponse>?>, t: Throwable) {
                    Log.e("Erro Request", "Request de busca de cursos falhou")
                    Toast.makeText(
                        this@SearchResultActivity,
                        "Falha na conexão!",
                        Toast.LENGTH_SHORT
                    ).show()
                    progress.visibility = View.GONE
                }
            }
        )

    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.let {
            setIntent(it)
            handleIntent(it)
        }
    }

    private fun handleIntent(intent: Intent) {
        val searchText = intent.getStringExtra("searchText")
        val textView = findViewById<TextView>(R.id.text_searchResult)
        textView.text = "Resultado da Busca: " + searchText
        fetchCourses(searchText.toString())
    }

    override fun onProfileClick() {
        println("Profile Clicked")
    }
}