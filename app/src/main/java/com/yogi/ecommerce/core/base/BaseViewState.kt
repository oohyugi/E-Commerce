package com.yogi.ecommerce.core.base

/**
 * Created by oohyugi on 2020-02-03.
 */
sealed class BaseViewState<out R> {
    object Loading : BaseViewState<Nothing>()
    data class Error(val errorMessage: String?) : BaseViewState<Nothing>()
    data class Success<T>(val data: T?, val isLast: Boolean? = false) : BaseViewState<T>()
}