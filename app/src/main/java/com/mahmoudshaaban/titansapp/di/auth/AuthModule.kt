package com.mahmoudshaaban.titansapp.di.auth

import android.content.SharedPreferences
import com.mahmoudshaaban.titansapp.api.auth.OpenApiAuthService
import com.mahmoudshaaban.titansapp.persistence.AccountPropertiesDao
import com.mahmoudshaaban.titansapp.persistence.AuthTokenDao
import com.mahmoudshaaban.titansapp.repository.auth.AuthRepository
import com.mahmoudshaaban.titansapp.session.SessionManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
class AuthModule{


    @AuthScope
    @Provides
    fun provideFakeApiService(retrofitBuilder : Retrofit.Builder): OpenApiAuthService {
        return retrofitBuilder
            .build()
            .create(OpenApiAuthService::class.java)
    }


    @AuthScope
    @Provides
    fun provideAuthRepository(
        sessionManager: SessionManager,
        authTokenDao: AuthTokenDao,
        accountPropertiesDao: AccountPropertiesDao,
        openApiAuthService: OpenApiAuthService ,
        sharedPreferences: SharedPreferences ,
        editor: SharedPreferences.Editor
    ): AuthRepository {
        return AuthRepository(
            authTokenDao,
            accountPropertiesDao,
            openApiAuthService,
            sessionManager ,
            sharedPreferences,
            editor
        )
    }

}