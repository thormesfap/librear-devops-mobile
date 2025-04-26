package br.com.librear.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import br.com.librear.R
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.drawable.DrawableCompat

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
                val imageResource = getResourceId(R.styleable.CourseDetailCard_courseDetailImage,
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

    private fun updateButtonVisibility() {
        val isLoggedIn = checkUserLoggedIn()
        val entrarButton = findViewById<TextView>(R.id.entrarButton)
        if(isLoggedIn && subscribeable){
            entrarButton.visibility = VISIBLE
        }else{
            entrarButton.visibility = GONE
        }


    }
    private fun checkUserLoggedIn():Boolean{
        val shared = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val isLoggedIn = shared.getBoolean("isLoggedIn", false)
        return isLoggedIn
    }
}