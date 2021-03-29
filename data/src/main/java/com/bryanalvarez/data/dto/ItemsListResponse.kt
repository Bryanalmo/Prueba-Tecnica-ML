package com.bryanalvarez.data.dto

import com.bryanalvarez.domain.models.Item
import com.bryanalvarez.domain.models.Paging
import java.io.Serializable

data class ItemsListResponse(
    val paging: Paging,
    val results: List<Item>
): Serializable