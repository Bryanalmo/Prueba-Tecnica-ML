package com.bryanalvarez.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.bryanalvarez.data.local.dao.UserSearchDao
import com.bryanalvarez.domain.models.UserSearch
import com.google.common.truth.Truth
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@SmallTest
class UserSearchDaoTest {

    private lateinit var database: MLDataBase
    private lateinit var userSearchDao: UserSearchDao

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), MLDataBase::class.java)
            .allowMainThreadQueries()
            .build()
        userSearchDao = database.userSearchDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun addUserSearch(){
        val userSearch = UserSearch("Play 5 Nuevo")
        userSearchDao.insert(userSearch)
        val searchList = userSearchDao.getUserSearchList()
        Truth.assertThat(searchList).contains(userSearch)
    }

    @Test
    fun getUserSearches(){
        val userSearch = UserSearch("Play 5 Nuevo")
        val userSearch2 = UserSearch("Iphone 5")
        userSearchDao.insert(userSearch)
        userSearchDao.insert(userSearch2)
        val searchList = userSearchDao.getUserSearchList()
        Truth.assertThat(searchList).hasSize(2)
    }
}