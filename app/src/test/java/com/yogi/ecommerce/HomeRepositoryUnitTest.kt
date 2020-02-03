package com.yogi.ecommerce


import com.yogi.ecommerce.core.models.BaseMdl
import com.yogi.ecommerce.core.utils.ResultState
import com.yogi.ecommerce.data.repos.HomeRepoImpl
import com.yogi.ecommerce.data.sources.remote.HomeRemoteDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

/**
 * Created by oohyugi on 2019-09-19.
 */
class HomeRepositoryUnitTest {


    private var source = mock(HomeRemoteDataSource::class.java)
    private lateinit var repo: HomeRepoImpl

    private val response = listOf<BaseMdl>()


    @Before
    fun setup() {
        repo = HomeRepoImpl(source)

    }

    @Test
    fun `should home success`() {
        val expected = ResultState.Success(response)

        val result = runBlocking {
            `when`(source.getHome())
                .thenReturn(ResultState.Success(response))

            repo.getHome()
        }
        assertEquals(expected, result)


    }

    @Test
    fun `should return error`() {
        val actual = ResultState.Error("")
        val result = runBlocking {
            `when`(source.getHome())
                .thenReturn(
                    ResultState.Error("")
                    )

            repo.getHome()
        }

        // probably has different error message, so you can check by type of java class
        assert(result.javaClass === actual.javaClass)
    }


}
