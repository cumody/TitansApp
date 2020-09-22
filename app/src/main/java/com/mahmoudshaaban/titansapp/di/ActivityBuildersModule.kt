package com.mahmoudshaaban.titansapp.di

import com.mahmoudshaaban.titansapp.di.auth.AuthFragmentBuildersModule
import com.mahmoudshaaban.titansapp.di.auth.AuthModule
import com.mahmoudshaaban.titansapp.di.auth.AuthScope
import com.mahmoudshaaban.titansapp.di.auth.AuthViewModelModule
import com.mahmoudshaaban.titansapp.di.main.MainFragmentBuildersModule
import com.mahmoudshaaban.titansapp.di.main.MainModule
import com.mahmoudshaaban.titansapp.di.main.MainScope
import com.mahmoudshaaban.titansapp.di.main.MainViewModelModule
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


    @MainScope
    @ContributesAndroidInjector(
        modules = [MainModule::class, MainFragmentBuildersModule::class, MainViewModelModule::class]
    )

    abstract fun contributeMainActivity(): MainActivity

}