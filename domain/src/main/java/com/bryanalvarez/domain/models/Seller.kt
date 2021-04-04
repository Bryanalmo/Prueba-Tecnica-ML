package com.bryanalvarez.domain.models

import androidx.room.Embedded
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Seller(
    @SerializedName("id") val idSeller: String? = null,
    val nickname: String? = null,
    val permalink: String? = null,
    @Embedded
    val seller_reputation: Reputation? = null
): Serializable