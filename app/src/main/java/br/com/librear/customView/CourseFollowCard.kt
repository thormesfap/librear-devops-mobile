package br.com.librear.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import br.com.librear.R
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.drawable.DrawableCompat

class CourseFollowCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val courseImageView: ImageView
    private val courseTitleTextView: TextView
    private val starsLayout: LinearLayout
    private val progressBar: ProgressBar

    init {
        LayoutInflater.from(context).inflate(R.layout.course_follow_card, this, true)
        courseImageView = findViewById(R.id.courseImageView)
        courseTitleTextView = findViewById(R.id.courseTitleTextView)
        starsLayout = findViewById(R.id.starsLayout)
        progressBar = findViewById(R.id.progressBar)
        orientation = VERTICAL

        attrs?.let {
            context.withStyledAttributes(it, R.styleable.CourseFollowCard, 0, 0) {
                val imageResource = getResourceId(
                    R.styleable.CourseFollowCard_courseFollowImage,
                    R.drawable.card_aprendendo_libras
                )
                val title = getString(R.styleable.CourseFollowCard_courseFollowTitle)
                val rating: Int = getInt(R.styleable.CourseFollowCard_courseFollowRating, -1)
                courseImageView.setImageDrawable(
                    AppCompatResources.getDrawable(
                        context,
                        imageResource
                    )
                )
                val progression: Int = getInt(R.styleable.CourseFollowCard_courseFollowCompletion, 0)
                progressBar.progress = progression
                courseTitleTextView.text = title
                if (rating > 0) {
                    setRating(rating)
                } else{
                    courseTitleTextView.textSize = 26F
                }
                val date: String? = getString(R.styleable.CourseFollowCard_courseFollowDate)
            }
        }
    }

    private fun setRating(rating: Int) {
        starsLayout.removeAllViews()
        for (i in 1..5) {
            val star = ImageView(context)
            val starDrawable = AppCompatResources.getDrawable(context, R.drawable.icon_star)
            val mutableDrawable = starDrawable?.mutate()
            val wrappedDrawable = DrawableCompat.wrap(mutableDrawable!!)
            if (i > rating) {
                wrappedDrawable.setTint(ContextCompat.getColor(context, R.color.action_disabled))
            }
            star.setImageDrawable(wrappedDrawable)
            val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            params.setMargins(0, 0, 4, 0)
            star.layoutParams = params
            starsLayout.addView(star)
        }
    }
}