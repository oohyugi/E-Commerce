package com.yogi.ecommerce.core.utils

import java.net.ConnectException

/**
 * Created by oohyugi on 2020-02-03.
 */
suspend fun <T : Any> fetchState(call: suspend () -> ResultState<T>): ResultState<T> {
    return try {
        call.invoke()
    } catch (e: ConnectException) {
        ResultState.Error(e.message)
    } catch (e: Exception) {

        ResultState.Error(e.message)
    } catch (e: Throwable) {
        ResultState.Error(e.message)

    }
}