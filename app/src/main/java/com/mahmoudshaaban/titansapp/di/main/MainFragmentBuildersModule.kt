package com.mahmoudshaaban.titansapp.di.main

import com.mahmoudshaaban.titansapp.ui.main.account.AccountFragment
import com.mahmoudshaaban.titansapp.ui.main.account.ChangePasswordFragment
import com.mahmoudshaaban.titansapp.ui.main.account.UpdateAccountFragment
import com.mahmoudshaaban.titansapp.ui.main.blog.BlogFragment
import com.mahmoudshaaban.titansapp.ui.main.blog.UpdateBlogFragment
import com.mahmoudshaaban.titansapp.ui.main.blog.ViewBlogFragment
import com.mahmoudshaaban.titansapp.ui.main.create_blog.CreateBlogFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector()
    abstract fun contributeBlogFragment(): BlogFragment

    @ContributesAndroidInjector()
    abstract fun contributeAccountFragment(): AccountFragment

    @ContributesAndroidInjector()
    abstract fun contributeChangePasswordFragment(): ChangePasswordFragment

    @ContributesAndroidInjector()
    abstract fun contributeCreateBlogFragment(): CreateBlogFragment

    @ContributesAndroidInjector()
    abstract fun contributeUpdateBlogFragment(): UpdateBlogFragment

    @ContributesAndroidInjector()
    abstract fun contributeViewBlogFragment(): ViewBlogFragment

    @ContributesAndroidInjector()
    abstract fun contributeUpdateAccountFragment(): UpdateAccountFragment
}