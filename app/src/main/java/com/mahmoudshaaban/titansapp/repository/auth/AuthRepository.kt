package com.mahmoudshaaban.titansapp.repository.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.mahmoudshaaban.titansapp.api.auth.OpenApiAuthService
import com.mahmoudshaaban.titansapp.api.network_responses.LoginResponse
import com.mahmoudshaaban.titansapp.api.network_responses.RegistrationResponse
import com.mahmoudshaaban.titansapp.models.AuthToken
import com.mahmoudshaaban.titansapp.persistence.AccountPropertiesDao
import com.mahmoudshaaban.titansapp.persistence.AuthTokenDao
import com.mahmoudshaaban.titansapp.session.SessionManager
import com.mahmoudshaaban.titansapp.ui.Data
import com.mahmoudshaaban.titansapp.ui.DataState
import com.mahmoudshaaban.titansapp.ui.Response
import com.mahmoudshaaban.titansapp.ui.ResponseType
import com.mahmoudshaaban.titansapp.ui.auth.state.AuthViewState
import com.mahmoudshaaban.titansapp.ui.auth.state.LoginFields
import com.mahmoudshaaban.titansapp.ui.auth.state.RegisterationFields
import com.mahmoudshaaban.titansapp.util.ApiEmptyResponse
import com.mahmoudshaaban.titansapp.util.ApiErrorResponse
import com.mahmoudshaaban.titansapp.util.ApiSuccessResponse
import com.mahmoudshaaban.titansapp.util.ErrorHandling.Companion.ERROR_UNKNOWN
import com.mahmoudshaaban.titansapp.util.ErrorHandling.Companion.GENERIC_AUTH_ERROR
import com.mahmoudshaaban.titansapp.util.GenericApiResponse
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.math.log

class AuthRepository
@Inject
constructor(
    val authTokenDao: AuthTokenDao,
    val accountPropertiesDao: AccountPropertiesDao,
    val openApiAuthService: OpenApiAuthService,
    val sessionManager: SessionManager
) {

    private val TAG = "AppDebug"

    private var repositoryJob: Job? = null

    fun attemptLogin(email: String, password: String): LiveData<DataState<AuthViewState>> {
        val loginFieldErrors = LoginFields(email, password).isValidForLogin()
        if (!loginFieldErrors.equals(LoginFields.LoginError.none())) {
            return returnErrorResponse(loginFieldErrors, ResponseType.None())
        }

        return object : NetworkBoundResource<LoginResponse, AuthViewState>(
            sessionManager.isConnectedToTheInternet()
        ) {
            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<LoginResponse>) {
                Log.d(TAG, "handleApiSuccessResponse ${response}}")

                // Incorrect Login credentials counts as a 200 response from a server , so need to handle that
                if (response.body.response.equals(GENERIC_AUTH_ERROR)) {
                    return onErrorReturn(response.body.errorMessage, true, false)

                }
                onCompleteJob(
                    DataState.data(
                        data = AuthViewState(
                            authToken = AuthToken(
                                response.body.pk
                                , response.body.token
                            )
                        )
                    )
                )

            }

            override fun createCall(): LiveData<GenericApiResponse<LoginResponse>> {
                return openApiAuthService.login(email, password)
            }

            override fun setJob(job: Job) {
                repositoryJob?.cancel()
                repositoryJob = job

            }

        }.asLiveData()

    }

    fun attemptRegistration(
        email: String,
        username: String,
        password: String,
        confirmPassword: String
    ): LiveData<DataState<AuthViewState>> {

        val registrationFieldErrors = RegisterationFields(
            email,
            username,
            password,
            confirmPassword
        ).isValidForRegisteration()
        if (!registrationFieldErrors.equals(RegisterationFields.RegisterationError.none())) {
            return returnErrorResponse(registrationFieldErrors, ResponseType.Dialog())
        }

        return object : NetworkBoundResource<RegistrationResponse, AuthViewState>(
            sessionManager.isConnectedToTheInternet()
        ) {
            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<RegistrationResponse>) {

                Log.d(TAG, "handleApiSuccessResponse: ${response}")

                if (response.body.response.equals(GENERIC_AUTH_ERROR)) {
                    return onErrorReturn(response.body.errorMessage, true, false)
                }

                onCompleteJob(
                    DataState.data(
                        data = AuthViewState(
                            authToken = AuthToken(response.body.pk, response.body.token)
                        )
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<RegistrationResponse>> {
                return openApiAuthService.register(email, username, password, confirmPassword)
            }

            override fun setJob(job: Job) {
                repositoryJob?.cancel()
                repositoryJob = job
            }

        }.asLiveData()
    }


    private fun returnErrorResponse(
        errormessage: String,
        responseType: ResponseType
    ): LiveData<DataState<AuthViewState>> {

        Log.d(TAG, "returnErrorResponse : ${errormessage}")
        return object : LiveData<DataState<AuthViewState>>() {
            override fun onActive() {
                super.onActive()
                value = DataState.error(
                    Response(
                        errormessage,
                        responseType
                    )
                )

            }
        }
    }

    fun cancelActiveJobs() {
        Log.d(TAG, "AuthRepository: Cancelling on going jobs ... ")
        repositoryJob?.cancel()
    }


}







