package com.mahmoudshaaban.titansapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.mahmoudshaaban.titansapp.ui.auth.AuthActivity
import com.mahmoudshaaban.titansapp.R
import com.mahmoudshaaban.titansapp.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tool_bar.setOnClickListener {
            sessionManager.logout()
        }

        subscribeObservers()
    }

    fun subscribeObservers(){
        sessionManager.cachedToken.observe(this, Observer{ authToken ->
            Log.d(TAG, "MainActivity, subscribeObservers: ViewState: ${authToken}")
            if(authToken == null || authToken.account_pk == -1 || authToken.token == null){
                navAuthActivity()
                finish()
            }
        })
    }

    private fun navAuthActivity(){
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }
}
