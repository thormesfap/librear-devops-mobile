package br.com.librear

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import br.com.librear.customView.Header
import com.google.android.material.navigation.NavigationView

open class BaseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    protected lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var header: Header

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    protected fun setupNavigationDrawer(drawerLayoutId: Int, headerId: Int, navigationViewId: Int) {
        drawerLayout = findViewById(drawerLayoutId)
        navigationView = findViewById(navigationViewId)
        header = findViewById<Header>(headerId)
        val menuButton = header.findViewById<ImageView>(R.id.menu)

        menuButton.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean{
        when (item.itemId){
            R.id.nav_home -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
            R.id.todos -> {
                val intent = Intent(this, SearchResultActivity::class.java)
                intent.putExtra("searchText", "")
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
            R.id.visual -> {
                val intent = Intent(this, SearchResultActivity::class.java)
                intent.putExtra("searchText", "visual")
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
            R.id.auditiva -> {
                val intent = Intent(this, SearchResultActivity::class.java)
                intent.putExtra("searchText", "auditiva")
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
            R.id.surdocegueira -> {
                val intent = Intent(this, SearchResultActivity::class.java)
                intent.putExtra("searchText", "surdocegueira")
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    override fun onBackPressed(){
        if(drawerLayout.isDrawerOpen(androidx.core.view.GravityCompat.START)){
            drawerLayout.closeDrawer(androidx.core.view.GravityCompat.START)
        } else{
            super.onBackPressed()
        }
    }
}