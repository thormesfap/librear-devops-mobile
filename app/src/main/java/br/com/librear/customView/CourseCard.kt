package br.com.librear.customView

import android.content.Context
import android.graphics.drawable.Drawable
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

class CourseCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val courseImageView: ImageView
    private val courseTitleTextView: TextView
    private val starsLayout: LinearLayout

    init{
        LayoutInflater.from(context).inflate(R.layout.course_card, this, true)
        courseImageView = findViewById(R.id.courseImageView)
        courseTitleTextView = findViewById(R.id.courseTitleTextView)
        starsLayout = findViewById(R.id.starsLayout)
        orientation = VERTICAL

        attrs?.let{
            context.withStyledAttributes(it, R.styleable.CourseCard, 0, 0) {
                val imageResource = getResourceId(
                    R.styleable.CourseCard_courseImage,
                    R.drawable.card_aprendendo_libras
                )
                val title = getString(R.styleable.CourseCard_courseTitle)
                val rating = getInt(R.styleable.CourseCard_courseRating, 0)
                courseImageView.setImageDrawable(
                    AppCompatResources.getDrawable(
                        context,
                        imageResource
                    )
                )
                courseTitleTextView.text = title
                setRating(rating)
            }
        }
    }

    private fun setRating(rating: Int){
        starsLayout.removeAllViews()
        for(i in 1..5){
            val star = ImageView(context)
            val starDrawable = AppCompatResources.getDrawable(context, R.drawable.icon_star)
            val mutableDrawable = starDrawable?.mutate()
            val wrappedDrawable = DrawableCompat.wrap(mutableDrawable!!)
            if(i > rating){
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