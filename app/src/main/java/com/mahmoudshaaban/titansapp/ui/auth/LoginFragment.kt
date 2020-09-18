package com.mahmoudshaaban.titansapp.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.mahmoudshaaban.titansapp.R
import com.mahmoudshaaban.titansapp.models.AuthToken
import com.mahmoudshaaban.titansapp.ui.auth.state.LoginFields
import com.mahmoudshaaban.titansapp.util.GenericApiResponse
import com.mahmoudshaaban.titansapp.util.GenericApiResponse.*
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : BaseAuthFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "LoginFragment: ${viewModel.hashCode()}")

        subscribeObservers()

        login_button.setOnClickListener{
            // set auth token to the view model
            viewModel.setAuthToken(
                AuthToken(1 , "1515511551")
            )
        }
    }

    fun subscribeObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            it.loginFields?.let { loginfields ->
                loginfields.login_email?.let { input_email.setText(it) }
                loginfields.login_password?.let { input_password.setText(it) }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.setLoginFields(
            LoginFields(
                input_email.text.toString() ,
                input_password.text.toString()
            )
        )
    }


}