package com.mahmoudshaaban.titansapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.mahmoudshaaban.titansapp.R
import com.mahmoudshaaban.titansapp.ui.BaseActivity
import com.mahmoudshaaban.titansapp.ui.auth.AuthActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tool_bar.setOnClickListener{
            sessionManager.logout()

        }

         subscribeObservers()

    }

    fun subscribeObservers(){
        sessionManager.cachedToken.observe(this , Observer {authtoken ->
            Log.d(TAG ,  "MainActivity : subscribeObservers : AuthToken ${authtoken}")
            if (authtoken == null || authtoken.account_pk == -1 || authtoken.token == null ){
                 navAuthActivity()
            }
        })
    }

    private fun navAuthActivity(){
        var intent = Intent(this,AuthActivity::class.java)
        startActivity(intent)
        finish()

    }


}