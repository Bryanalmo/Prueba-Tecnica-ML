package com.bryanalvarez.domain.interactors

import androidx.paging.PageKeyedDataSource
import androidx.paging.PagingSource
import arrow.core.Either
import arrow.core.Failure
import com.bryanalvarez.domain.models.Item
import com.bryanalvarez.domain.repository.Repository
/*
class ItemsBySearchDataSource(val search: String,
                              private val repository: Repository
)
    : PagingSource<Int, Item>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        /*val nextPage = params.key ?: 1
        repository.getItemsBySearch(search).fold(
                {

                },
                {
                    return LoadResult.Page(
                        data = it,
                        prevKey = if (nextPage == 1) null else nextPage - 1,
                        nextKey = nextPage + 1
                    )
                }
        )
    }


}*/