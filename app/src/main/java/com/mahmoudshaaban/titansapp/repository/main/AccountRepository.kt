package com.mahmoudshaaban.titansapp.repository.main

import android.util.Log
import com.mahmoudshaaban.titansapp.api.main.OpenApiMainService
import com.mahmoudshaaban.titansapp.persistence.AccountPropertiesDao
import com.mahmoudshaaban.titansapp.session.SessionManager
import kotlinx.coroutines.Job
import javax.inject.Inject


class AccountRepository
@Inject
constructor(
    val openApiMainService: OpenApiMainService,
    val accountPropertiesDao: AccountPropertiesDao,
    val sessionManager: SessionManager
) {
    private val TAG = "AppDebug"

    private var repositoryJob : Job? = null

    fun cancelActiveJobs(){
        Log.d(TAG, "AuthRepository: cancelling on-going jobs ... ")
    }
}