package com.yogi.ecommerce.core.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProductPromoItemMdl(@SerializedName("loved")
                               val loved: Int = 0,
                               @SerializedName("price")
                               val price: String = "",
                               @SerializedName("imageUrl")
                               val imageUrl: String = "",
                               @SerializedName("description")
                               val description: String = "",
                               @SerializedName("id")
                               val id: Any? = null,
                               @SerializedName("title")
                               val title: String = "",
                               var qty: Int = 0
) : Serializable