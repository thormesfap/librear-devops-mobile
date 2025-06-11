package br.com.librear.customView

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import br.com.librear.R
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.drawable.DrawableCompat
import br.com.librear.AulaActivity
import br.com.librear.CourseResponse
import br.com.librear.LoginActivity
import br.com.librear.MsgResponse
import br.com.librear.RetrofitInstance
import coil3.load
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CourseDetailCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val courseImageView: ImageView
    private val courseTitleTextView: TextView
    private val courseDescriptionTextView: TextView
    private val lessonAmountTextView: TextView
    private val readingsAmountTextView: TextView
    private var subscribeable: Boolean = false


    init {
        LayoutInflater.from(context).inflate(R.layout.course_detail_card, this, true)
        courseImageView = findViewById(R.id.courseImageView)
        courseTitleTextView = findViewById(R.id.courseTitleTextView)
        courseDescriptionTextView = findViewById(R.id.courseDescriptionTextView)
        lessonAmountTextView = findViewById(R.id.lessonsAmountTextView)
        readingsAmountTextView = findViewById(R.id.readingsAmountTextView)
        orientation = VERTICAL

        attrs?.let {
            context.withStyledAttributes(it, R.styleable.CourseDetailCard, 0, 0) {
                val imageResource = getResourceId(
                    R.styleable.CourseDetailCard_courseDetailImage,
                    R.drawable.card_aprendendo_libras
                )
                val title = getString(R.styleable.CourseDetailCard_courseDetailTitle)
                val description = getString(R.styleable.CourseDetailCard_courseDetailDescription)
                val lessons = getInt(R.styleable.CourseDetailCard_lessonsAmount, 0)
                val readings = getInt(R.styleable.CourseDetailCard_readingsAmount, 0)
                subscribeable = getBoolean(R.styleable.CourseDetailCard_sunscribleable, false)

                courseImageView.setImageDrawable(
                    AppCompatResources.getDrawable(
                        context,
                        imageResource
                    )
                )
                courseTitleTextView.text = title
                courseDescriptionTextView.text = description
                lessonAmountTextView.text = lessons.toString() + " aulas"
                readingsAmountTextView.text = readings.toString() + " leituras"
                updateButtonVisibility()
            }
        }
    }


    private fun updateButtonVisibility(course: CourseResponse? = null) {
        val isLoggedIn = checkUserLoggedIn()
        val entrarButton = findViewById<TextView>(R.id.entrarButton)
        val shared = context.getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val meusCursos = shared.getStringSet("meusCursos", setOf())
        if (course != null) {
            entrarButton.visibility = VISIBLE
            if (meusCursos?.contains(course.id.toString()) == true) {
                entrarButton.text = "Entrar no Curso"
                entrarButton.setOnClickListener {
                    val intent = Intent(context, AulaActivity::class.java)
                    intent.putExtra("aulaId", course.aulas?.get(0)?.id)
                    context.startActivity(intent)
                }
            } else {
                entrarButton.text = "Matricular-se"
                entrarButton.setOnClickListener {
                    if (isLoggedIn) {
                        RetrofitInstance.apiInterface.matricular(course.id)
                            .enqueue(object : Callback<MsgResponse> {
                                override fun onResponse(
                                    call: Call<MsgResponse?>,
                                    response: Response<MsgResponse?>
                                ) {
                                    if (response.isSuccessful) {
                                        val shared = context.getSharedPreferences("AppPrefs",MODE_PRIVATE)
                                        val meusCursos =shared.getStringSet("meusCursos", setOf())?.toMutableSet()
                                        meusCursos?.add(course.id.toString())
                                        shared.edit { putStringSet("meusCursos", meusCursos) }
                                        Toast.makeText(context, "Matrícula realizada com sucesso", Toast.LENGTH_LONG).show()
                                    }
                                     else{
                                        Toast.makeText(context, "Não foi possível realizar matrícula no curso", Toast.LENGTH_LONG).show()
                                    }
                                }

                                override fun onFailure(call: Call<MsgResponse?>, t: Throwable) {
                                    Toast.makeText(
                                        this@CourseDetailCard.context,
                                        "Não foi possível realizar matrícula no curso: ${t.message}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            })
                    } else {
                        val intent = Intent(context, LoginActivity::class.java)
                        context.startActivity(intent)
                    }
                }
            }
        }
    }

    private fun checkUserLoggedIn(): Boolean {
        val shared = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val isLoggedIn = shared.getBoolean("isLoggedIn", false)
        return isLoggedIn
    }

    fun setCourseData(course: CourseResponse) {
        courseImageView.load(course.capaUrl)
        courseTitleTextView.text = course.titulo
        courseDescriptionTextView.text = course.descricao
        lessonAmountTextView.text = course.aulas?.size.toString() + " aulas"
        readingsAmountTextView.text = course.leituras?.size.toString() + " leituras"
        updateButtonVisibility(course)

    }
}