package com.mahmoudshaaban.titansapp.di

import android.app.Application
import com.mahmoudshaaban.titansapp.BaseApplication
import com.mahmoudshaaban.titansapp.session.SessionManager
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityBuildersModule::class,
        ViewModelFactoryModule::class
    ]
)
interface AppComponent : AndroidInjector<BaseApplication>{

    // we put sessionManager here b/c we need it in every where in our application => viewmodel , repository , anywhere
    val sessionManager: SessionManager // must add here b/c injecting into abstract class

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
