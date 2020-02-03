package com.yogi.ecommerce.feature.home.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.yogi.ecommerce.core.base.BaseViewState
import com.yogi.ecommerce.core.models.BaseMdl
import com.yogi.ecommerce.core.models.ProductPromoItemMdl
import com.yogi.ecommerce.core.utils.ResultState
import com.yogi.ecommerce.core.utils.SingleLiveEvent
import com.yogi.ecommerce.data.repos.HomeRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val homeRepo: HomeRepo) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _responseHome = MutableLiveData<BaseViewState<List<BaseMdl>>>()
    val responseDataHome: LiveData<BaseViewState<List<BaseMdl>>> = _responseHome

    private val _navigateToDetail = SingleLiveEvent<ProductPromoItemMdl>()
    val navigateToDetail: SingleLiveEvent<ProductPromoItemMdl> get() =_navigateToDetail

    init {
        loadHome()
    }


    private fun loadHome() {
        _responseHome.value = BaseViewState.Loading
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                homeRepo.getHome()
            }
            when (result) {
                is ResultState.Success -> {
                    _responseHome.value = BaseViewState.Success(result.data)

                }
                is ResultState.Error -> _responseHome.value =
                    BaseViewState.Error(result.errorMessage)
            }
        }

    }

    fun onUserClickItemProduct(it: ProductPromoItemMdl) {

        _navigateToDetail.value = it
    }
}