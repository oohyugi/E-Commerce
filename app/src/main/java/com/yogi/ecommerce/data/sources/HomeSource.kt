package com.yogi.ecommerce.data.sources

import com.yogi.ecommerce.core.models.Basemdl
import com.yogi.ecommerce.core.utils.ResultState

/**
 * Created by oohyugi on 2020-02-03.
 */
interface HomeSource {

    suspend fun getHome(): ResultState<List<Basemdl>>
}