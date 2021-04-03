package com.bryanalvarez.domain.models

import java.io.Serializable

data class Seller(
    val id: String? = null,
    val nickname: String? = null,
    val permalink: String? = null,
    val seller_reputation: Reputation? = null
): Serializable