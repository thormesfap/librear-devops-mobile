package br.com.librear.customView

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import br.com.librear.R
import androidx.core.content.edit
import br.com.librear.LoginActivity
import br.com.librear.MainActivity
import br.com.librear.ProfileActivity
import br.com.librear.SearchResultActivity
import com.google.android.material.textfield.TextInputLayout

class Header @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), SharedPreferences.OnSharedPreferenceChangeListener {

    private val profileButton: ImageView
    private val searchField: EditText
    private val logo: ImageView
    private var listener: OnProfileClickListener? = null
    private var searchListener: OnSearchListener? = null
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    private lateinit var searchContainer: TextInputLayout

    interface OnProfileClickListener {
        fun onProfileClick()
    }

    interface OnSearchListener {
        fun onSearchSubmit(searchText: String)
    }
    interface OnLoginStateChangeListener {
        fun onLoginStateChanged(isLoggedIn: Boolean)
    }

    private var loginStateChangeListener: OnLoginStateChangeListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.header, this, true)
        profileButton = findViewById(R.id.profile)
        searchField = findViewById(R.id.search)
        logo = findViewById(R.id.logo)
        searchContainer = findViewById(R.id.search_container)

        logo.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
        searchContainer.setEndIconOnClickListener {
            val searchText = searchField.text.toString()
            performSearch(searchText)
        }

        profileButton.setOnClickListener {
            val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
            if(isLoggedIn){
                val intent = Intent(context, ProfileActivity::class.java)
                context.startActivity(intent)
            } else{
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
            }
        }



        searchField.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                val searchText = searchField.text.toString()
               performSearch(searchText)
                true
            } else {
                false
            }
        }


        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        updateProfileImage()

    }

    private fun performSearch(searchText: String) {
        val intent = Intent(context, SearchResultActivity::class.java)
        intent.putExtra("searchText", searchText)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        context.startActivity(intent)
    }

    fun setOnProfileClickListener(listener: OnProfileClickListener) {
        this.listener = listener
    }

    fun setOnSearchListener(listener: OnSearchListener) {
        this.searchListener = listener
    }

    fun setOnLoginStateChangeListener(listener: OnLoginStateChangeListener) {
        this.loginStateChangeListener = listener
    }

    private fun updateProfileImage() {
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        val imageRes = if (isLoggedIn) R.drawable.profile_image else R.drawable.icon_profile
        profileButton.setImageResource(imageRes)
        if(isLoggedIn){
            profileButton.setBackgroundResource(R.drawable.circular)
            profileButton.clipToOutline = true
            profileButton.scaleType = ImageView.ScaleType.FIT_XY
            profileButton.adjustViewBounds = true

        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, p1: String?) {
        if (p1 == "isLoggedIn") {
            updateProfileImage()
        }
    }


    fun updateLoginState(isLoggedIn: Boolean){
        sharedPreferences.edit() { putBoolean("isLoggedIn", isLoggedIn) }
    }
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}