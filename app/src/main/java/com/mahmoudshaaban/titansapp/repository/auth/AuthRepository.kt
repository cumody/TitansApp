package com.mahmoudshaaban.titansapp.repository.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.switchMap
import com.mahmoudshaaban.titansapp.R
import com.mahmoudshaaban.titansapp.ui.BaseActivity
import com.mahmoudshaaban.titansapp.ui.ResponseType
import com.mahmoudshaaban.titansapp.ui.main.MainActivity
import com.mahmoudshaaban.titansapp.viewmodels.ViewModelProviderFactory
import com.mahmoudshaaban.titansapp.api.auth.OpenApiAuthService
import com.mahmoudshaaban.titansapp.models.AuthToken
import com.mahmoudshaaban.titansapp.persistence.AccountPropertiesDao
import com.mahmoudshaaban.titansapp.persistence.AuthTokenDao
import com.mahmoudshaaban.titansapp.session.SessionManager
import com.mahmoudshaaban.titansapp.ui.DataState
import com.mahmoudshaaban.titansapp.ui.Response
import com.mahmoudshaaban.titansapp.ui.auth.state.AuthViewState
import com.mahmoudshaaban.titansapp.util.ErrorHandling.Companion.ERROR_UNKNOWN
import com.mahmoudshaaban.titansapp.util.GenericApiResponse
import javax.inject.Inject

class AuthRepository
@Inject
constructor(
    val authTokenDao: AuthTokenDao,
    val sessionManger: AccountPropertiesDao,
    val openApiAuthService: OpenApiAuthService,
    val accountPropertiesDao: SessionManager
) {

    fun attemptLogin(email: String, password: String): LiveData<DataState<AuthViewState>> {
        return openApiAuthService.login(email, password)
            .switchMap { response ->
                object : LiveData<DataState<AuthViewState>>() {
                    override fun onActive() {
                        super.onActive()
                        when (response) {
                            is GenericApiResponse.ApiSuccessResponse -> {
                                value = DataState.data(
                                    data = AuthViewState(
                                        authToken = AuthToken(
                                            response.body.pk,
                                            response.body.token
                                        )
                                    ), response = null
                                )

                            }
                            is GenericApiResponse.ApiErrorResponse -> {
                                value = DataState.error(
                                    response = Response(
                                        message = response.errorMessage,
                                        responseType = ResponseType.Dialog()

                                    )
                                )


                            }
                            is GenericApiResponse.ApiEmptyResponse -> {
                                value = DataState.error(
                                    response = Response(
                                        message = ERROR_UNKNOWN,
                                        responseType = ResponseType.Dialog()

                                    )
                                )

                            }
                        }
                    }
                }


            }

    }

    fun attemptRegisteration(
        email: String,
        username: String,
        password: String,
        confirm_password: String
    )
            : LiveData<DataState<AuthViewState>> {
        return openApiAuthService.register(email , username, password , confirm_password)
            .switchMap { response ->
                object : LiveData<DataState<AuthViewState>>() {
                    override fun onActive() {
                        super.onActive()
                        when (response) {
                            is GenericApiResponse.ApiSuccessResponse -> {
                                value = DataState.data(
                                    data = AuthViewState(
                                        authToken = AuthToken(
                                            response.body.pk,
                                            response.body.token
                                        )
                                    ), response = null
                                )

                            }
                            is GenericApiResponse.ApiErrorResponse -> {
                                value = DataState.error(
                                    response = Response(
                                        message = response.errorMessage,
                                        responseType = ResponseType.Dialog()

                                    )
                                )


                            }
                            is GenericApiResponse.ApiEmptyResponse -> {
                                value = DataState.error(
                                    response = Response(
                                        message = ERROR_UNKNOWN,
                                        responseType = ResponseType.Dialog()

                                    )
                                )

                            }
                        }
                    }
                }


            }

    }
}