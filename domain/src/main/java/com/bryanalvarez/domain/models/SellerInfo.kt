package com.bryanalvarez.domain.models

import java.io.Serializable

data class SellerInfo(
    val seller: Seller,
    val results: List<Item>
): Serializable