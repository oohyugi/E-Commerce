package com.yogi.ecommerce

import android.app.Application
import com.yogi.ecommerce.core.di.coreModule
import com.yogi.ecommerce.data.di.dataModule
import com.yogi.ecommerce.feature.detail.di.detailModule
import com.yogi.ecommerce.feature.home.di.homeModule
import com.yogi.ecommerce.feature.purchased.di.purchasedModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp : Application() {


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            androidFileProperties()
            modules(listOf(coreModule, dataModule, homeModule, detailModule, purchasedModule))
        }
    }
}