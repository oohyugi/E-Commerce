package com.yogi.ecommerce


import com.yogi.ecommerce.core.models.BaseMdl
import com.yogi.ecommerce.core.utils.ResultState
import com.yogi.ecommerce.data.sources.remote.ApiServices
import com.yogi.ecommerce.data.sources.remote.HomeRemoteDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import retrofit2.Response

/**
 * Created by oohyugi on 2019-09-19.
 */
class HomeDataSourceUnitTest {


    private var apiService = mock(ApiServices::class.java)
    private lateinit var source: HomeRemoteDataSource

    private val response = listOf<BaseMdl>()


    @Before
    fun setup() {
        source = HomeRemoteDataSource(apiService)

    }

    @Test
    fun `should home success`()  = runBlocking {
        `when`(apiService.getHomeApi()).thenReturn(
            Response.success(response)
        )

        val repo = source.getHome()

        assertEquals(ResultState.Success(""), repo)

    }

    @Test
    fun `should return error`() {
        val actual = ResultState.Error("")
        val result = runBlocking {
            `when`(source.getHome())
                .thenReturn(
                    ResultState.Error("")
                    )

            source.getHome()
        }

        // probably has different error message, so you can check by type of java class
        assert(result.javaClass === actual.javaClass)
    }


}
