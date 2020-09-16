package com.mahmoudshaaban.titansapp.di

import androidx.lifecycle.ViewModelProvider
import com.mahmoudshaaban.titansapp.viewmodels.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}