package com.bryanalvarez.domain.models

import com.bryanalvarez.domain.models.Item
import com.bryanalvarez.domain.models.Paging
import java.io.Serializable

data class ItemsListInfo(
    val paging: Paging,
    val results: MutableList<Item>
): Serializable