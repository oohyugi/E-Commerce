package com.yogi.ecommerce.data.di

import com.yogi.ecommerce.BuildConfig
import com.yogi.ecommerce.data.sources.remote.ApiClient
import com.yogi.ecommerce.data.sources.remote.ApiServices
import com.yogi.ecommerce.data.sources.remote.HomeRemoteDataSource
import org.koin.dsl.module

/**
 * Created by oohyugi on 2020-02-03.
 */

val dataModule = module {

    fun provideApiService(baseUrl: String): ApiServices {

        return ApiClient.retrofitClient(baseUrl).create(ApiServices::class.java)
    }
    single {
        provideApiService(BuildConfig.API_URL)
    }

    
    fun provideHomeSource(apiServices: ApiServices): HomeRemoteDataSource {
        return HomeRemoteDataSource(apiServices)
    }
    factory {
        provideHomeSource(get())
    }



}
