package com.yogi.ecommerce.feature.purchased.di


import com.yogi.ecommerce.feature.purchased.PurchasedViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val purchasedModule = module {


    viewModel {
        PurchasedViewModel()
    }
}