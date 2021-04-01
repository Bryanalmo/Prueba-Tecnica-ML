package com.bryanalvarez.domain.models

import com.google.common.truth.Truth
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ItemTest{

    var item = Item(price = 135000F, condition = "new")

    @Test
    fun `generate item formatted price`(){
        val priceFormatted = item.getPriceFormatted()
        Truth.assertThat(priceFormatted).isEqualTo("135.000")
    }

    @Test
    fun `get condition translated`(){
        val conditionTranslated = item.getConditionTranslate()
        Truth.assertThat(conditionTranslated).isEqualTo("Nuevo")
    }
}