package com.yogi.ecommerce.data.sources.remote

import com.yogi.ecommerce.core.models.Basemdl
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by oohyugi on 2020-02-03.
 */
interface ApiServices {

    @GET("/home")
    suspend fun getHomeApi(): Response<List<Basemdl>>
}