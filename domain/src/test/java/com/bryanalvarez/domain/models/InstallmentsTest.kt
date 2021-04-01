package com.bryanalvarez.domain.models

import com.google.common.truth.Truth
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class InstallmentsTest{

    var installments = Installments(10, 13500.0F)

    @Test
    fun `generate installment ammount formatted`(){
        val amountFormatted = installments.getAmountFormatted()
        Truth.assertThat(amountFormatted).isEqualTo("13.500")
    }
}