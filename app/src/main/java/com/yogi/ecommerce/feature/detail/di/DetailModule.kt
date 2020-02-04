package com.yogi.ecommerce.feature.detail.di

import com.yogi.ecommerce.feature.detail.DetailViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by oohyugi on 2020-02-04.
 */

val detailModule = module {

    viewModel {
        DetailViewModel()
    }
}