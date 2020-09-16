package com.mahmoudshaaban.titansapp.repository.auth

import androidx.lifecycle.LiveData
import com.mahmoudshaaban.titansapp.api.auth.OpenApiAuthService
import com.mahmoudshaaban.titansapp.api.network_responses.LoginResponse
import com.mahmoudshaaban.titansapp.api.network_responses.RegistrationResponse
import com.mahmoudshaaban.titansapp.persistence.AccountPropertiesDao
import com.mahmoudshaaban.titansapp.persistence.AuthTokenDao
import com.mahmoudshaaban.titansapp.session.SessionManager
import com.mahmoudshaaban.titansapp.util.GenericApiResponse
import javax.inject.Inject

class AuthRepository
@Inject
constructor(
    val authTokenDao: AuthTokenDao,
    val sessionManger: AccountPropertiesDao,
    val openApiAuthService: OpenApiAuthService,
    val accountPropertiesDao: SessionManager
)
{
 fun testLoginRequest(email : String , password : String) : LiveData<GenericApiResponse<LoginResponse>>{
     return openApiAuthService.login(email,password)
 }

    fun testRegisterRequest( email : String , username : String , password : String , confirmpassword : String) : LiveData<GenericApiResponse<RegistrationResponse>>{
        return openApiAuthService.register(email,username , password, confirmpassword)
    }
}