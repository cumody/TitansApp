package com.mahmoudshaaban.titansapp.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mahmoudshaaban.titansapp.api.network_responses.LoginResponse
import com.mahmoudshaaban.titansapp.api.network_responses.RegistrationResponse
import com.mahmoudshaaban.titansapp.models.AuthToken
import com.mahmoudshaaban.titansapp.repository.auth.AuthRepository
import com.mahmoudshaaban.titansapp.ui.BaseViewModel
import com.mahmoudshaaban.titansapp.ui.DataState
import com.mahmoudshaaban.titansapp.ui.auth.state.AuthStateEvent
import com.mahmoudshaaban.titansapp.ui.auth.state.AuthStateEvent.*
import com.mahmoudshaaban.titansapp.ui.auth.state.AuthViewState
import com.mahmoudshaaban.titansapp.ui.auth.state.LoginFields
import com.mahmoudshaaban.titansapp.ui.auth.state.RegisterationFields
import com.mahmoudshaaban.titansapp.util.AbsentLiveData
import com.mahmoudshaaban.titansapp.util.GenericApiResponse
import javax.inject.Inject

class AuthViewModel
@Inject
constructor(
    val authRepository: AuthRepository
): BaseViewModel<AuthStateEvent, AuthViewState>()
{
    override fun handleStateEvent(stateEvent: AuthStateEvent): LiveData<DataState<AuthViewState>> {
        when(stateEvent){

            is LoginAttemptEvent -> {
                return authRepository.attemptLogin(
                    stateEvent.email,
                    stateEvent.password
                )
            }

            is RegisterAttemptEvent -> {
                return authRepository.attemptRegistration(
                    stateEvent.email,
                    stateEvent.username,
                    stateEvent.password,
                    stateEvent.confirmPassword
                )
            }

            is CheckPreviousAuthEvent -> {
                return AbsentLiveData.create()
            }


        }
    }

    fun setRegistrationFields(registrationFields: RegisterationFields){
        val update = getCurrentViewStateOrNew()
        if(update.registerationFields == registrationFields){
            return
        }
        update.registerationFields = registrationFields
        _viewState.value = update
    }

    fun setLoginFields(loginFields: LoginFields){
        val update = getCurrentViewStateOrNew()
        if(update.loginFields == loginFields){
            return
        }
        update.loginFields = loginFields
        _viewState.value = update
    }

    fun setAuthToken(authToken: AuthToken){
        val update = getCurrentViewStateOrNew()
        if(update.authToken == authToken){
            return
        }
        update.authToken = authToken
        _viewState.value = update
    }


    override fun initNewViewState(): AuthViewState {
        return AuthViewState()
    }
}



