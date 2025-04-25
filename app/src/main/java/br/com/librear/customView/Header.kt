package br.com.librear.customView

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import br.com.librear.R

class Header @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val profileButton: ImageView
    private val searchField: EditText
    private var listener: OnProfileClickListener? = null
    private var searchListener: OnSearchListener? = null

    interface OnProfileClickListener {
        fun onProfileClick()
    }

    interface OnSearchListener {
        fun onSearchSubmit(searchText: String)
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.header, this, true)
        profileButton = findViewById(R.id.profile)
        searchField = findViewById(R.id.search)

        profileButton.setOnClickListener {
            listener?.onProfileClick()
        }

        searchField.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                val searchText = searchField.text.toString()
                searchListener?.onSearchSubmit(searchText)
                true
            } else {
                false
            }
        }
    }

    fun setOnProfileClickListener(listener: OnProfileClickListener) {
        this.listener = listener
    }

    fun setOnSearchListener(listener: OnSearchListener) {
        this.searchListener = listener
    }
}