package com.mahmoudshaaban.titansapp.repository.auth

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.mahmoudshaaban.titansapp.api.auth.OpenApiAuthService
import com.mahmoudshaaban.titansapp.api.network_responses.LoginResponse
import com.mahmoudshaaban.titansapp.api.network_responses.RegistrationResponse
import com.mahmoudshaaban.titansapp.models.AccountProperties
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
import com.mahmoudshaaban.titansapp.util.*
import com.mahmoudshaaban.titansapp.util.ErrorHandling.Companion.ERROR_SAVE_AUTH_TOKEN
import com.mahmoudshaaban.titansapp.util.ErrorHandling.Companion.ERROR_UNKNOWN
import com.mahmoudshaaban.titansapp.util.ErrorHandling.Companion.GENERIC_AUTH_ERROR
import com.mahmoudshaaban.titansapp.util.SuccessHandling.Companion.RESPONSE_CHECK_PREVIOUS_AUTH_USER_DONE
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.math.log

class AuthRepository
@Inject
constructor(
    val authTokenDao: AuthTokenDao,
    val accountPropertiesDao: AccountPropertiesDao,
    val openApiAuthService: OpenApiAuthService,
    val sessionManager: SessionManager ,
    val sharedPreferences: SharedPreferences ,
    val sharedPrefsEditor : SharedPreferences.Editor
) {

    private val TAG = "AppDebug"

    private var repositoryJob: Job? = null

    fun attemptLogin(email: String, password: String): LiveData<DataState<AuthViewState>> {
        val loginFieldErrors = LoginFields(email, password).isValidForLogin()
        if (!loginFieldErrors.equals(LoginFields.LoginError.none())) {
            return returnErrorResponse(loginFieldErrors, ResponseType.Dialog())
        }

        return object : NetworkBoundResource<LoginResponse, Any , AuthViewState>(
            sessionManager.isConnectedToTheInternet() ,
            true ,
            true ,
            false
        ) {
            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<LoginResponse>) {
                Log.d(TAG, "handleApiSuccessResponse ${response}}")

                // Incorrect Login credentials counts as a 200 response from a server , so need to handle that
                if (response.body.response.equals(GENERIC_AUTH_ERROR)) {
                    return onErrorReturn(response.body.errorMessage, true, false)

                }

                // don't care about result , Just insert if it doesn't exist b/c foreign key relationship
                // WITH AUTH TOKEN TABLE
                accountPropertiesDao.insertOrIgnore(
                    AccountProperties(
                        response.body.pk,
                        response.body.email,
                        ""
                    )
                )
                // will return -1 if failure
                val result = authTokenDao.insert(
                    AuthToken(response.body.pk
                    , response.body.token)
                )
                if (result < 0){
                    return onCompleteJob(
                        DataState.error(
                            Response(ERROR_SAVE_AUTH_TOKEN , ResponseType.Dialog())
                        )
                    )
                }

                saveAuthenticatedUserToPrefs(email)

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

            // not used in this case
            override suspend fun createCacheRequestAndReturn() {

            }

            // ignore
            override fun loadFromCache(): LiveData<AuthViewState> {
                return AbsentLiveData.create()
            }

            // ignore
            override suspend fun updateLocalDb(cacheObject: Any?) {
                TODO("Not yet implemented")
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

        return object : NetworkBoundResource<RegistrationResponse, Any , AuthViewState>(
            sessionManager.isConnectedToTheInternet() ,
            true ,
            true ,
            false
        ) {

            // not used in this case
            override suspend fun createCacheRequestAndReturn() {

            }

            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<RegistrationResponse>) {

                Log.d(TAG, "handleApiSuccessResponse: ${response}")

                if (response.body.response.equals(GENERIC_AUTH_ERROR)) {
                    return onErrorReturn(response.body.errorMessage, true, false)
                }


                saveAuthenticatedUserToPrefs(email)



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

            // ignore
            override fun loadFromCache(): LiveData<AuthViewState> {
                return AbsentLiveData.create()
            }

            // ignore
            override suspend fun updateLocalDb(cacheObject: Any?) {
                TODO("Not yet implemented")
            }

        }.asLiveData()
    }

    fun checkPreviousAuthUser() : LiveData<DataState<AuthViewState>>{
        val previousAuthUserEmail : String? =
            sharedPreferences.getString(PreferenceKeys.PREVIOUS_AUTH_USER , null)
        if (previousAuthUserEmail.isNullOrBlank()){
            Log.d(TAG, "checkPreviousAuthUser: No Previously authenticated user found ..")
            return returnNoTokenFound()
        }
        return object : NetworkBoundResource<Void , Any , AuthViewState>(
            sessionManager.isConnectedToTheInternet() ,
            false ,
            false ,
            false
        ) {
            override suspend fun createCacheRequestAndReturn() {
                accountPropertiesDao.searchByEmail(previousAuthUserEmail).let { accountProperties ->
                    Log.d(TAG, "checkPreviousAuthUser: searching for the token: $accountProperties ")

                    accountProperties?.let {
                        if (accountProperties.pk > -1 ){
                            authTokenDao.searchByPk(accountProperties.pk).let {authtoken->
                                if (authtoken != null){
                                    onCompleteJob(
                                        DataState.data(data = AuthViewState(
                                            authToken = authtoken
                                        ))
                                    )
                                    return
                                }
                            }
                        }
                    }
                    Log.d(TAG, "checkPreviousAuthUser: AuthToken notfound ... ")
                    onCompleteJob(
                        DataState.data(
                            data = null,
                            response = Response(
                                RESPONSE_CHECK_PREVIOUS_AUTH_USER_DONE ,
                                ResponseType.None()
                            )
                        )
                    )
                }
            }

            // not used in this case
            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<Void>) {

            }

            // not used in this case
            override fun createCall(): LiveData<GenericApiResponse<Void>> {
                return AbsentLiveData.create()
            }

            override fun setJob(job: Job) {
                repositoryJob?.cancel()
                repositoryJob = job
            }

            // ignore
            override fun loadFromCache(): LiveData<AuthViewState> {
                return AbsentLiveData.create()
            }

            // ignore
            override suspend fun updateLocalDb(cacheObject: Any?) {
                TODO("Not yet implemented")
            }

        }.asLiveData()
    }

    private fun returnNoTokenFound(): LiveData<DataState<AuthViewState>> {
        return object : LiveData<DataState<AuthViewState>>(){
            override fun onActive() {
                super.onActive()
                value = DataState.data(
                    data = null ,
                    response = Response(RESPONSE_CHECK_PREVIOUS_AUTH_USER_DONE , ResponseType.None())
                )
            }
        }

    }

    private fun saveAuthenticatedUserToPrefs(email: String) {
        sharedPrefsEditor.putString(PreferenceKeys.PREVIOUS_AUTH_USER, email)
        sharedPrefsEditor.apply()

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







