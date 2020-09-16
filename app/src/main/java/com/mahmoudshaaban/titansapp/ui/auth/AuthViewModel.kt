package com.mahmoudshaaban.titansapp.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mahmoudshaaban.titansapp.api.network_responses.LoginResponse
import com.mahmoudshaaban.titansapp.api.network_responses.RegistrationResponse
import com.mahmoudshaaban.titansapp.repository.auth.AuthRepository
import com.mahmoudshaaban.titansapp.util.GenericApiResponse
import javax.inject.Inject

class AuthViewModel
@Inject
constructor
    (val authRepository: AuthRepository) : ViewModel() {


    fun testLogin() : LiveData<GenericApiResponse<LoginResponse>>{
        return authRepository.testLoginRequest("mitchelltabian@gmail.com" , "codingwithmitch1")
    }
    fun testRegister() : LiveData<GenericApiResponse<RegistrationResponse>>{
        return authRepository.testRegisterRequest(
            "mitchelltabian1234@gmail.com" ,
            "mitchelltabian1234",
        "codingwithmitch1" ,
            "codingwithmitch1")
    }

}