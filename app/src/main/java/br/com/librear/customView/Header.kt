package br.com.librear.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import br.com.librear.R

class Header @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val profileButton: ImageView
    private var listener: OnProfileClickListener? = null

    interface OnProfileClickListener {
        fun onProfileClick()
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.header, this, true)
        profileButton = findViewById(R.id.profile)

        profileButton.setOnClickListener {
            listener?.onProfileClick()
        }
    }

    fun setOnProfileClickListener(listener: OnProfileClickListener) {
        this.listener = listener
    }
}