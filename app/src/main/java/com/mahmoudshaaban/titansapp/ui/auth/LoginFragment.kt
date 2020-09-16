package com.mahmoudshaaban.titansapp.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.mahmoudshaaban.titansapp.R
import com.mahmoudshaaban.titansapp.util.GenericApiResponse
import com.mahmoudshaaban.titansapp.util.GenericApiResponse.*


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

        viewModel.testLogin().observe(viewLifecycleOwner, Observer { response ->

            when (response) {
                is ApiSuccessResponse -> {
                    Log.d(TAG, "LOGIN RESPONSE :  ${response.body}")

                }
                is ApiErrorResponse -> {
                    Log.d(TAG, "LOGIN RESPONSE: ${response.errorMessage}")

                }
                is ApiEmptyResponse -> {
                    Log.d(TAG, "LOGIN RESPONSE : EMPTY RESPONSE ")

                }
            }


        })


    }


}