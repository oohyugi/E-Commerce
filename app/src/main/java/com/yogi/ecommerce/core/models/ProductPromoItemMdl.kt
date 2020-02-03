package com.yogi.ecommerce.core.models

import com.google.gson.annotations.SerializedName

data class ProductPromoItemMdl(@SerializedName("loved")
                               val loved: Int = 0,
                               @SerializedName("price")
                               val price: String = "",
                               @SerializedName("imageUrl")
                               val imageUrl: String = "",
                               @SerializedName("description")
                               val description: String = "",
                               @SerializedName("id")
                               val id: String = "",
                               @SerializedName("title")
                               val title: String = "")