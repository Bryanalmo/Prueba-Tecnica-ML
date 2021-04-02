package com.bryanalvarez.mlsearch.mockRepository

import arrow.core.Either
import arrow.core.Failure
import arrow.core.Left
import arrow.core.Right
import com.bryanalvarez.domain.models.*
import com.bryanalvarez.domain.repository.Repository

class AppMockedRepository: Repository {

    private var itemsList = mutableListOf<Item>()
    var recentSearchesList = mutableListOf<UserSearch>()
    private var categoriesList = mutableListOf<Category>()
    private var shouldReturnError = false

    init {
        setupMockedData()
    }

    fun setShouldReturnError(value: Boolean){ shouldReturnError = value }

    override suspend fun getItemsBySearch(text: String, offset: Int): Either<Failure, ItemsListInfo> {
        return try {
            if(!shouldReturnError && text.isNotEmpty()){
                var itemsListFiltered = itemsList.filter { item -> item.title!!.contains(text, true) }
                Right(ItemsListInfo(
                    Paging(0,50),
                    itemsListFiltered.toMutableList()
                ))
            }else{
                Left(Failure(Throwable("")))
            }
        }catch (e: Exception){
            Left(Failure(Throwable(e.localizedMessage)))
        }
    }

    override suspend fun getUserRecentSearch(): Either<Failure, List<UserSearch>> {
        return try {
            if(!shouldReturnError){
                Right(recentSearchesList)
            }else{
                Left(Failure(Throwable("")))
            }
        }catch (e: Exception){
            Left(Failure(Throwable(e.localizedMessage)))
        }
    }

    override suspend fun addUserSearch(text: String): Either<Failure, Boolean> {
        return try {
            if(!shouldReturnError && text.isNotEmpty()){
                recentSearchesList.add(UserSearch(text))
                Right(true)
            }else{
                Left(Failure(Throwable()))
            }
        }catch (e: Exception){
            Left(Failure(Throwable()))
        }
    }

    override suspend fun getCategories(): Either<Failure, List<Category>> {
        return try {
            if(!shouldReturnError){
                Right(categoriesList)
            }else{
                Left(Failure(Throwable("")))
            }
        }catch (e: Exception){
            Left(Failure(Throwable(e.localizedMessage)))
        }
    }

    override suspend fun getItemsByCategory(categoryId: String, offset: Int): Either<Failure, ItemsListInfo> {
        return try {
            if(!shouldReturnError && categoryId.isNotEmpty()){
                Right(ItemsListInfo(
                    Paging(0,50),
                    itemsList
                ))
            }else{
                Left(Failure(Throwable("")))
            }
        }catch (e: Exception){
            Left(Failure(Throwable(e.localizedMessage)))
        }
    }

    override suspend fun getItemsBySeller(sellerId: String): Either<Failure, SellerInfo> {
        return try {
            if(!shouldReturnError && sellerId.isNotEmpty()){
                Right(SellerInfo(
                    Seller("id", "mocked seller", "url", Reputation("5_green", "gold")),
                    itemsList.filter { item -> item.seller?.id == sellerId }
                ))
            }else{
                Left(Failure(Throwable("")))
            }
        }catch (e: Exception){
            Left(Failure(Throwable(e.localizedMessage)))
        }
    }

    private fun setupMockedData(){
        itemsList.add(Item(title = "Iphone 5", seller = Seller(id = "id")))
        itemsList.add(Item(title = "Iphone 4", seller = Seller(id = "id")))
        itemsList.add(Item(title = "Iphone 3", seller = Seller(id = "id")))
        itemsList.add(Item(title = "Xbox 360", seller = Seller(id = "id2")))
        itemsList.add(Item(title = "Armario", seller = Seller(id = "id3")))

        recentSearchesList.add(UserSearch("Play 5 Nuevo"))
        recentSearchesList.add(UserSearch("Iphone 5"))

        categoriesList.add(Category("id", "cat 1"))
        categoriesList.add(Category("id2", "cat 2"))
        categoriesList.add(Category("id3", "cat 3"))

    }
}