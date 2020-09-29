package com.mahmoudshaaban.titansapp.di.main


import com.mahmoudshaaban.titansapp.api.main.OpenApiMainService
import com.mahmoudshaaban.titansapp.persistence.AccountPropertiesDao
import com.mahmoudshaaban.titansapp.repository.main.AccountRepository
import com.mahmoudshaaban.titansapp.session.SessionManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainModule {

    @MainScope
    @Provides
    fun providesOpenApiMainService(retrofitBuilder: Retrofit.Builder): OpenApiMainService {
        return retrofitBuilder.build()
            .create(OpenApiMainService::class.java)
    }

    @MainScope
    @Provides
    fun provideAccountRepository(
        openApiMainService: OpenApiMainService
        , accountPropertiesDao: AccountPropertiesDao
        , sessionManager: SessionManager
    ): AccountRepository {
        return AccountRepository(openApiMainService, accountPropertiesDao, sessionManager)


    }


}