package com.yogi.ecommerce.core.helpers

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yogi.ecommerce.core.models.ProductPromoItemMdl

/**
 * Created by oohyugi on 2019-09-19.
 */

class PrefHelper(private val context: Context) {

    companion object {
        const val PREF_USER_LOGIN = "pref_user_login"
        const val PREF_PURCHASED_HISTORY = "pref_purchased_history"
    }


    private fun pref(): SharedPreferences {
        return context.getSharedPreferences("e-commerce", Context.MODE_PRIVATE)
    }

    fun saveHistory(data: ProductPromoItemMdl?) {

        val listHistory: MutableList<ProductPromoItemMdl> = mutableListOf()
        getHistory()?.let { listHistory.addAll(it) }
        data?.let {
            listHistory.add(data)
        }

        pref().edit().putString(PREF_PURCHASED_HISTORY, Gson().toJson(listHistory)).apply()
    }

    fun getHistory(): MutableList<ProductPromoItemMdl>? {

        val typeToken = object : TypeToken<MutableList<ProductPromoItemMdl>>() {}.type
        val jsonString = pref().getString(
            PREF_PURCHASED_HISTORY, null
        )
        var listHistory: MutableList<ProductPromoItemMdl>? = mutableListOf()
        jsonString?.apply {
            listHistory = Gson().fromJson<MutableList<ProductPromoItemMdl>>(
                jsonString, typeToken
            )
        }
        return listHistory


    }

    fun saveUser(name: String?) {
        pref().edit().putString(PREF_USER_LOGIN, name).apply()
    }

    fun getUser(): String? {
        return pref().getString(PREF_USER_LOGIN, null)
    }


}
