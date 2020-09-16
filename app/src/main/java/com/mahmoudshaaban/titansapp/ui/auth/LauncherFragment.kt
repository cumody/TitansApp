package com.mahmoudshaaban.titansapp.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mahmoudshaaban.titansapp.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_launcher.*


class LauncherFragment : BaseAuthFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_launcher, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "LauncherFragment: ${viewModel.hashCode()}")

        register.setOnClickListener() {
            navRegisteration()
        }
        login.setOnClickListener() {
            navLogin()
        }
        forgot_password.setOnClickListener() {
            navForgetPassword()
        }

        focusable_view.requestFocus()

    }

    private fun navForgetPassword() {
        findNavController().navigate(R.id.action_launcherFragment_to_forgetPasswordFragment)

    }

    private fun navLogin() {
        findNavController().navigate(R.id.action_launcherFragment_to_loginFragment)

    }

    private fun navRegisteration() {
        findNavController().navigate(R.id.action_launcherFragment_to_registerFragment)


    }


}