package com.bryanalvarez.domain.models

import java.io.Serializable

data class Address(
    val state_name: String? = null,
    val city_name: String? = null
): Serializable {
    /**
     * function to return the address in the format "state, city"
     */
    fun getAddressFormatted(): String { return "$state_name, $city_name" }
}