package com.yogi.ecommerce.feature.home.di

import com.yogi.ecommerce.data.repos.HomeRepo
import com.yogi.ecommerce.data.repos.HomeRepoImpl
import com.yogi.ecommerce.data.sources.remote.HomeRemoteDataSource
import com.yogi.ecommerce.feature.home.ui.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {

    fun provideHomeRepo(homeSource: HomeRemoteDataSource): HomeRepoImpl {
        return HomeRepoImpl(homeSource)
    }

    factory<HomeRepo> {
        provideHomeRepo(get())
    }

    viewModel {
        HomeViewModel(homeRepo = get())
    }
}