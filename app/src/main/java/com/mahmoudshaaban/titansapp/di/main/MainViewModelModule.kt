package com.mahmoudshaaban.titansapp.di.main

import androidx.lifecycle.ViewModel
import com.mahmoudshaaban.titansapp.di.ViewModelKey
import com.mahmoudshaaban.titansapp.ui.auth.AuthViewModel
import com.mahmoudshaaban.titansapp.ui.main.account.AccountViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    abstract fun bindAuthViewModel(accountViewModel: AccountViewModel): ViewModel


}
