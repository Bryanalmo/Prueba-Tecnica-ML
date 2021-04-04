package com.bryanalvarez.domain.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.text.DecimalFormat

@Entity(tableName = "lastSeenItem")
data class Item(
    @PrimaryKey val id: String = "",
    val title: String? = null,
    val price: Float? = null,
    val thumbnail: String? = null,
    @Embedded
    val installments: Installments? = null,
    @Embedded
    val shipping: Shipping? = null,
    @Embedded
    val seller: Seller? = null,
    val available_quantity: Int? = null,
    val sold_quantity: Int? = null,
    val condition: String? = null,
    val accepts_mercadopago: Boolean? = null,
    @Embedded
    val address: Address? = null,
    val attributes: List<Attribute>? = null
): Serializable {

    /**
     * function to get the price formatted
     */
    fun getPriceFormatted (): String? {
        return try {
            val formatter = DecimalFormat("#,###")
            formatter.format(price?.toInt())
        } catch (e: Exception) {
            price.toString()
        }
    }

    /**
     * function to get the item condition translated
     */
    fun getConditionTranslate(): String {
        return when(condition){
            "new" -> "Nuevo"
            else -> "Usado"
        }
    }
}