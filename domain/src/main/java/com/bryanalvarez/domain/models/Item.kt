package com.bryanalvarez.domain.models

import java.io.Serializable
import java.text.DecimalFormat

data class Item(
    val id: String? = null,
    val title: String? = null,
    val price: Float? = null,
    val thumbnail: String? = null,
    val installments: Installments? = null,
    val shipping: Shipping? = null,
    val seller: Seller? = null,
    val available_quantity: Int? = null,
    val sold_quantity: Int? = null,
    val condition: String? = null,
    val accepts_mercadopago: Boolean? = null,
    val address: Address? = null,
    val attributes: List<Attribute>? = null
): Serializable {

    fun getPriceFormatted (): String? {
        return try {
            val formatter = DecimalFormat("#,###")
            formatter.format(price?.toInt())
        } catch (e: Exception) {
            price.toString()
        }
    }

    fun getConditionTranslate(): String {
        return if(condition == "new"){
            "Nuevo"
        }else{
            "Usado"
        }
    }
}