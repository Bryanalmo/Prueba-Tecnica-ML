package com.bryanalvarez.domain.models

import java.io.Serializable
import java.text.DecimalFormat

data class Installments(
    val quantity : Int,
    val amount : Float
): Serializable {
    fun getAmountFormatted (): String? {
        return try {
            val formatter = DecimalFormat("#,###")
            formatter.format(amount?.toInt())
        } catch (e: Exception) {
            amount.toString()
        }
    }
}