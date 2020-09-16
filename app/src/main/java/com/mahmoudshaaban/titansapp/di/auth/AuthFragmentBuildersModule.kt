package com.mahmoudshaaban.titansapp.di.auth

import com.mahmoudshaaban.titansapp.ui.auth.ForgetPasswordFragment
import com.mahmoudshaaban.titansapp.ui.auth.LauncherFragment
import com.mahmoudshaaban.titansapp.ui.auth.LoginFragment
import com.mahmoudshaaban.titansapp.ui.auth.RegisterFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AuthFragmentBuildersModule {

    @ContributesAndroidInjector()
    abstract fun contributeLauncherFragment(): LauncherFragment

    @ContributesAndroidInjector()
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector()
    abstract fun contributeRegisterFragment(): RegisterFragment

    @ContributesAndroidInjector()
    abstract fun contributeForgotPasswordFragment(): ForgetPasswordFragment

}