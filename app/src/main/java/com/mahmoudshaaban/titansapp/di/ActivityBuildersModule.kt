package com.mahmoudshaaban.titansapp.di

import com.mahmoudshaaban.titansapp.di.auth.AuthFragmentBuildersModule
import com.mahmoudshaaban.titansapp.di.auth.AuthModule
import com.mahmoudshaaban.titansapp.di.auth.AuthScope
import com.mahmoudshaaban.titansapp.di.auth.AuthViewModelModule
import com.mahmoudshaaban.titansapp.ui.auth.AuthActivity
import com.mahmoudshaaban.titansapp.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(
        modules = [AuthModule::class, AuthFragmentBuildersModule::class, AuthViewModelModule::class]
    )
    abstract fun contributeAuthActivity(): AuthActivity

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}