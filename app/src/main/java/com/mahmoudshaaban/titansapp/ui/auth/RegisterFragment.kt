package com.mahmoudshaaban.titansapp.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.mahmoudshaaban.titansapp.R
import com.mahmoudshaaban.titansapp.ui.auth.state.LoginFields
import com.mahmoudshaaban.titansapp.ui.auth.state.RegisterationFields
import com.mahmoudshaaban.titansapp.util.GenericApiResponse
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.input_email
import kotlinx.android.synthetic.main.fragment_login.input_password
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

        Log.d(TAG, "RegisterFragment: ${viewModel.hashCode()}")


      subscribeObservers()

    }

    fun subscribeObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            it.registerationFields?.let { registerationFields ->
                registerationFields.registeration_email?.let {  input_email.setText(it) }
                registerationFields.registeration_username?.let {input_username.setText(it) }
                registerationFields.registeration_password?.let {input_password.setText(it) }
                registerationFields.registeration_confirmPassword?.let {input_password_confirm.setText(it) }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.setRegisterationFields(
            RegisterationFields(
                input_email.text.toString() ,
                input_username.text.toString() ,
                input_password.text.toString() ,
                input_password_confirm.text.toString()
            )
        )
    }

}