package com.yogi.ecommerce.core.di

import com.yogi.ecommerce.core.helpers.PrefHelper
import org.koin.dsl.module

/**
 * Created by oohyugi on 2020-02-03.
 */


val coreModule = module {

    single {
        PrefHelper(context = get())
    }
    
}