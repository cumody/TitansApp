package com.mahmoudshaaban.titansapp.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.mahmoudshaaban.titansapp.R
import com.mahmoudshaaban.titansapp.ui.auth.state.AuthStateEvent
import com.mahmoudshaaban.titansapp.ui.auth.state.AuthStateEvent.*
import com.mahmoudshaaban.titansapp.ui.auth.state.RegisterationFields
import com.mahmoudshaaban.titansapp.util.ApiEmptyResponse
import com.mahmoudshaaban.titansapp.util.ApiErrorResponse
import com.mahmoudshaaban.titansapp.util.ApiSuccessResponse
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : BaseAuthFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "RegisterFragment: ${viewModel}")

        register_button.setOnClickListener {
            register()
        }
        subscribeObservers()
    }

    fun subscribeObservers(){
        viewModel.viewState.observe(viewLifecycleOwner, Observer{viewState ->
            viewState.registerationFields?.let {
                it.registeration_email?.let{input_email.setText(it)}
                it.registeration_username?.let{input_username.setText(it)}
                it.registeration_password?.let{input_password.setText(it)}
                it.registeration_confirmPassword?.let{input_password_confirm.setText(it)}
            }
        })
    }

    fun register(){
        viewModel.setStateEvent(
            RegisterAttemptEvent(
                input_email.text.toString(),
                input_username.text.toString(),
                input_password.text.toString(),
                input_password_confirm.text.toString()
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.setRegistrationFields(
            RegisterationFields(
                input_email.text.toString(),
                input_username.text.toString(),
                input_password.text.toString(),
                input_password_confirm.text.toString()
            )
        )
    }
}
