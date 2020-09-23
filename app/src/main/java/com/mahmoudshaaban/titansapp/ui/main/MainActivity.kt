package com.mahmoudshaaban.titansapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mahmoudshaaban.titansapp.R
import com.mahmoudshaaban.titansapp.ui.BaseActivity
import com.mahmoudshaaban.titansapp.ui.auth.AuthActivity
import com.mahmoudshaaban.titansapp.ui.main.account.ChangePasswordFragment
import com.mahmoudshaaban.titansapp.ui.main.account.UpdateAccountFragment
import com.mahmoudshaaban.titansapp.ui.main.blog.UpdateBlogFragment
import com.mahmoudshaaban.titansapp.ui.main.blog.ViewBlogFragment
import com.mahmoudshaaban.titansapp.util.BottomNavController
import com.mahmoudshaaban.titansapp.util.BottomNavController.*
import com.mahmoudshaaban.titansapp.util.setUpNavigation
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(),
    NavGraphProvider,
    OnNavigationGraphChanged,
    OnNavigationReselectedListener {


    private lateinit var bottomNavigationView : BottomNavigationView

    private val bottomNavController by lazy (LazyThreadSafetyMode.NONE ){
        BottomNavController(
            this,
            R.id.main_fragments_container,
            R.id.nav_blog ,
            this ,
            this
        )

    }


    override fun displayProgressBar(bool: Boolean) {
        if (bool) {
            progress_bar.visibility == View.VISIBLE
        } else {
            progress_bar.visibility = View.INVISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar()
        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.setUpNavigation(bottomNavController,this)
        if (savedInstanceState == null){
            bottomNavController.onNavigationItemSelected()
        }


        tool_bar.setOnClickListener {
            sessionManager.logout()
        }

        subscribeObservers()
    }

    private fun setSupportActionBar(){
        setSupportActionBar(tool_bar)
    }


    override fun onBackPressed() =  bottomNavController.onBackPressed()

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> onBackPressed()
        }


        return super.onOptionsItemSelected(item)

    }



    fun subscribeObservers() {
        sessionManager.cachedToken.observe(this, Observer { authToken ->
            Log.d(TAG, "MainActivity, subscribeObservers: ViewState: ${authToken}")
            if (authToken == null || authToken.account_pk == -1 || authToken.token == null) {
                navAuthActivity()
                finish()
            }
        })
    }

    private fun navAuthActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun getNavGraphId(itemId: Int) = when(itemId) {
        R.id.nav_blog -> {
            R.navigation.nav_blog
        }
        R.id.nav_account -> {
            R.navigation.nav_account
        }
        R.id.nav_create_blog -> {
            R.navigation.nav_create_blog
        } else -> {
            R.navigation.nav_blog

        }

    }

    override fun onGraphChange() {
        expandAppBar()

    }

    override fun onReselectNavItem(navController: NavController, fragment: Fragment) = when(fragment){

        is ViewBlogFragment -> {
            navController.navigate(R.id.action_viewBlogFragment_to_blogFragment)
        }
        is UpdateBlogFragment -> {
            navController.navigate(R.id.action_updateBlogFragment_to_blogFragment)
        }
        is UpdateAccountFragment -> {
            navController.navigate(R.id.action_updateAccountFragment_to_accountFragment)
        }
        is ChangePasswordFragment -> {
            navController.navigate(R.id.action_changePasswordFragment_to_accountFragment)
        }
        else -> {
            // DO NOTHING
        }

    }

    override fun expandAppBar() {
        findViewById<AppBarLayout>(R.id.app_bar).setExpanded(true)
    }
}
