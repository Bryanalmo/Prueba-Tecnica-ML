package com.bryanalvarez.domain.models

import java.io.Serializable

data class Paging (
    val offset : Int,
    val limit: Int
): Serializable