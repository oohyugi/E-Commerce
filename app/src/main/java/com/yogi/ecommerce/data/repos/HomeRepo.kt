package com.yogi.ecommerce.data.repos

import com.yogi.ecommerce.core.models.BaseMdl
import com.yogi.ecommerce.core.utils.ResultState
import com.yogi.ecommerce.data.sources.remote.HomeRemoteDataSource

/**
 * Created by oohyugi on 2020-02-03.
 */
interface HomeRepo {


    suspend fun getHome(): ResultState<List<BaseMdl>>


}

class HomeRepoImpl(private val remoteDataSource: HomeRemoteDataSource) : HomeRepo {
    override suspend fun getHome(): ResultState<List<BaseMdl>> {
        return remoteDataSource.getHome()
    }

}