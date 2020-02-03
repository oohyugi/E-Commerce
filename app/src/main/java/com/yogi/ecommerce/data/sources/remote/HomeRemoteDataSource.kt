package com.yogi.ecommerce.data.sources.remote

import com.yogi.ecommerce.core.models.BaseMdl
import com.yogi.ecommerce.core.utils.ResultState
import com.yogi.ecommerce.core.utils.fetchState
import com.yogi.ecommerce.data.sources.HomeSource

/**
 * Created by oohyugi on 2020-02-03.
 */
class HomeRemoteDataSource(private val apiServices: ApiServices) : HomeSource {
    override suspend fun getHome(): ResultState<List<BaseMdl>> {
        return fetchState {
            val response = apiServices.getHomeApi()
            if (response.isSuccessful) {
                ResultState.Success(response.body())
            } else {
                ResultState.Error(response.message())
            }
        }
    }

}