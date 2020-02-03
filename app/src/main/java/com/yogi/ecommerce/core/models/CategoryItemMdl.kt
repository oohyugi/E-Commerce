package com.yogi.ecommerce.core.models

import com.google.gson.annotations.SerializedName

data class CategoryItemMdl(@SerializedName("imageUrl")
                           val imageUrl: String = "",
                           @SerializedName("name")
                           val name: String = "",
                           @SerializedName("id")
                           val id: Int = 0)