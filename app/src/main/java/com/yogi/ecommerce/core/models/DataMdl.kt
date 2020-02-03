package com.yogi.ecommerce.core.models

import com.google.gson.annotations.SerializedName

data class DataMdl(@SerializedName("productPromo")
                   val productPromo: List<ProductPromoItemMdl>?,
                   @SerializedName("category")
                   val category: List<CategoryItemMdl>?)