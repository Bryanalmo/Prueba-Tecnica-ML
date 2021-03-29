package com.bryanalvarez.domain.models

import java.io.Serializable
import java.text.DecimalFormat

data class Item(
    val id: String,
    val title: String,
    val price: Float,
    val thumbnail: String,
    val installments: Installments,
    val shipping: Shipping
): Serializable {

    fun getPriceFormatted (): String? {
        return try {
            val formatter = DecimalFormat("#,###")
            formatter.format(price?.toInt())
        } catch (e: Exception) {
            price.toString()
        }
    }
}