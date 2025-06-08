package br.com.librear.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.withStyledAttributes
import br.com.librear.R

class ActivityStrip @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val activityImage: ImageView
    private val activitySequence: TextView
    private val activityName: TextView
    private val activityMinutes: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.strip_activity, this, true)
        activityImage = findViewById(R.id.strip_icon)
        activitySequence = findViewById(R.id.activity_sequence)
        activityName = findViewById(R.id.activity_name)
        activityMinutes = findViewById(R.id.activity_minutes)

//        attrs?.let {
//            context.withStyledAttributes(it, R.styleable.StripActivity, 0, 0) {
//                var imageResource = getResourceId(
//                    R.styleable.StripActivity_stripActivityImage,
//                    R.drawable.icon_camera
//                )
//                activityName.text = getString(R.styleable.StripActivity_stripActivityName)
//                val sequence = getInt(R.styleable.StripActivity_stripActivitySequence, 0)
//                activitySequence.text = sequence.toString().padStart(2, '0')
//                val minutes = getInt(R.styleable.StripActivity_stripActivityMinutes, 0)
//                activityImage.setImageDrawable(
//                    AppCompatResources.getDrawable(
//                        context,
//                        imageResource
//                    )
//                )
//                activityMinutes.text = minutes.toString().padStart(2, '0') + " min"
//                if (minutes == 0) {
//                    activityMinutes.visibility = GONE
//                    activityImage.setImageDrawable(
//                        AppCompatResources.getDrawable(
//                            context,
//                            R.drawable.icon_book
//                        )
//                    )
//                }
//
//            }
//        }


    }

    fun setStripData(strip: StripInterface) {
        activitySequence.text = strip.sequencia.toString().padStart(2, '0')
        activityName.text = strip.titulo
        var imageResource = R.drawable.icon_camera
        if (strip.duracaoMinutos == null) {
            activityMinutes.visibility = GONE
            imageResource = R.drawable.icon_book
        } else {
            activityMinutes.text = strip.duracaoMinutos.toString().padStart(2, '0') + " min"
        }
        activityImage.setImageDrawable(AppCompatResources.getDrawable(context, imageResource))

    }

}