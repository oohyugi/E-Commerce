package com.yogi.ecommerce.core.utils

/**
 * Created by oohyugi on 2020-02-03.
 */
sealed class ResultState<out T : Any?> {
    data class Success<out T : Any?>(val data: T?, val isLast: Boolean = false) : ResultState<T>()
    data class Error(val errorMessage: String?) : ResultState<Nothing>()
}