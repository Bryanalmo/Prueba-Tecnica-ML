package com.bryanalvarez.domain.models

import java.io.Serializable

data class Address(
    val state_name: String? = null,
    val city_name: String? = null
): Serializable {
    fun getAddressFormatted(): String { return "$state_name, $city_name" }
}