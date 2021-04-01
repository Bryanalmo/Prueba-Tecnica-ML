package com.bryanalvarez.domain.models

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AddressTest{

    var address = Address("state", "city")

    @Test
    fun `generate address formatted`(){
        val addressFormatted = address.getAddressFormatted()
        assertThat(addressFormatted).isEqualTo("state, city")
    }
}