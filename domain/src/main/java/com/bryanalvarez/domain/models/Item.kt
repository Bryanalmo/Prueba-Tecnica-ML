package com.bryanalvarez.domain.models

import java.io.Serializable

data class Item(
    val id: String,
    val title: String,
    val price: Float,
    val thumbnail: String
): Serializable