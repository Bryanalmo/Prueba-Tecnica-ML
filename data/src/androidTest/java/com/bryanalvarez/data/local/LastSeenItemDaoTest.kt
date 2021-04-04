package com.bryanalvarez.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.bryanalvarez.data.local.dao.LastSeenItemDao
import com.bryanalvarez.domain.models.Item
import com.bryanalvarez.domain.models.Seller
import com.google.common.truth.Truth
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LastSeenItemDaoTest {
    private lateinit var database: MLDataBase
    private lateinit var lastSeenItemDao: LastSeenItemDao

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), MLDataBase::class.java)
            .allowMainThreadQueries()
            .build()
        lastSeenItemDao = database.lastSeenItemDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun addAndGetLastSeenItem(){
        val lastSeenItem = Item(title = "Armario", seller = Seller(idSeller = "id3"))
        lastSeenItemDao.insert(lastSeenItem)
        val itemSaved = lastSeenItemDao.getLastSeenItem()
        Truth.assertThat(itemSaved.title).isEqualTo(lastSeenItem.title)
    }

}