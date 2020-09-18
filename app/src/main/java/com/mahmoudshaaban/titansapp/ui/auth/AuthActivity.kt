package com.mahmoudshaaban.titansapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mahmoudshaaban.titansapp.R
import com.mahmoudshaaban.titansapp.ui.BaseActivity
import com.mahmoudshaaban.titansapp.ui.main.MainActivity
import com.mahmoudshaaban.titansapp.viewmodels.ViewModelProviderFactory
import javax.inject.Inject

class AuthActivity : BaseActivity() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        viewModel = ViewModelProvider(this , providerFactory).get(AuthViewModel::class.java)

        subscribeObservers()


    }

    fun subscribeObservers(){

        //if the token set in viewstate that mean that observer is triggerd and will token set to session manager
        // then cahced togged will triggerd and go to main activity

        viewModel.viewState.observe(this , Observer {
            it.authToken?.let {
                sessionManager.login(it)
            }
        })

        sessionManager.cachedToken.observe(this , Observer {authtoken ->
            Log.d(TAG ,  "AuthActivity : subscribeObservers : AuthToken ${authtoken}")
            if (authtoken != null && authtoken.account_pk != -1 && authtoken.token != null ){
                navMainActivity()
            }
        })
    }

    fun navMainActivity(){
        var intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()


    }
}