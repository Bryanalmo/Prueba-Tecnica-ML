package com.bryanalvarez.data.remote

import android.util.Log
import arrow.core.Either
import arrow.core.Failure
import arrow.core.Left
import arrow.core.Right
import com.bryanalvarez.data.local.repository.UserSearchRepository
import com.bryanalvarez.domain.constants.PAGE_LIMIT
import com.bryanalvarez.domain.models.*
import com.bryanalvarez.domain.repository.Repository
import org.json.JSONException
import java.lang.Exception

class AppRepository(private val service: Service,
                    private val userSearchRepository: UserSearchRepository): Repository {

    override suspend fun getItemsBySearch(text: String, offset: Int): Either<Failure, ItemsListInfo> {
        return try{
            val response = service.getItemsBySearch(text, PAGE_LIMIT, offset).execute()
            val data = response.body()
            val code = response.code()
            if(code == 200 && response.isSuccessful && data != null){
                Right(data)
            }else{
                Left(Failure(Throwable()))
            }
        }catch (ex: JSONException) {
            Log.d("MYLOG", "getItemsBySearch ERROR JSON -> ${ex.localizedMessage}")
            Left(Failure(Throwable()))
        }  catch (e: Exception) {
            Log.d("MYLOG", "getItemsBySearch ERROR EX -> ${e.localizedMessage}")
            Left(Failure(Throwable(e.message.toString())))
        }
    }

    override suspend fun getUserRecentSearch(): Either<Failure, List<UserSearch>> {
        return try{
            val data = userSearchRepository.getUserSearchList()
            if(data != null){
                Right(data)
            }else{
                Left(Failure(Throwable()))
            }
        }catch (e: Exception) {
            Log.d("MYLOG", "getUserRecentSearch ERROR EX -> ${e.localizedMessage}")
            Left(Failure(Throwable(e.message.toString())))
        }
    }

    override suspend fun addUserSearch(text: String): Either<Failure, Boolean> {
        return try{
            val data = userSearchRepository.insertItem(UserSearch(textSearched = text))
            if(data != null){
                Right(true)
            }else{
                Left(Failure(Throwable()))
            }
        }catch (e: Exception) {
            Log.d("MYLOG", "addUserSearch ERROR EX -> ${e.localizedMessage}")
            Left(Failure(Throwable(e.message.toString())))
        }
    }

    override suspend fun getCategories(): Either<Failure, List<Category>> {
        return try{
            val response = service.getCategories().execute()
            val data = response.body()
            val code = response.code()
            if(code == 200 && response.isSuccessful && data != null){
                Right(data)
            }else{
                Left(Failure(Throwable("Parece que no tienes internet, intentalo nuevamente")))
            }
        }catch (ex: JSONException) {
            Log.d("MYLOG", "getCategories ERROR JSON -> ${ex.localizedMessage}")
            Left(Failure(Throwable()))
        }  catch (e: Exception) {
            Log.d("MYLOG", "getCategories ERROR EX -> ${e.localizedMessage}")
            Left(Failure(Throwable(e.message.toString())))
        }
    }

    override suspend fun getItemsByCategory(categoryId: String, offset: Int): Either<Failure, ItemsListInfo> {
        return try{
            val response = service.getItemsByCategory(categoryId, PAGE_LIMIT, offset).execute()
            val data = response.body()
            val code = response.code()
            if(code == 200 && response.isSuccessful && data != null){
                Right(data)
            }else{
                Left(Failure(Throwable()))
            }
        }catch (ex: JSONException) {
            Log.d("MYLOG", "getItemsByCategory ERROR JSON -> ${ex.localizedMessage}")
            Left(Failure(Throwable()))
        }  catch (e: Exception) {
            Log.d("MYLOG", "getItemsByCategory ERROR EX -> ${e.localizedMessage}")
            Left(Failure(Throwable(e.message.toString())))
        }
    }

    override suspend fun getItemsBySeller(sellerId: String): Either<Failure, SellerInfo> {
        return try{
            val response = service.getItemsBySeller(sellerId).execute()
            val data = response.body()
            val code = response.code()
            if(code == 200 && response.isSuccessful && data != null){
                Right(data)
            }else{
                Left(Failure(Throwable()))
            }
        }catch (ex: JSONException) {
            Log.d("MYLOG", "getItemsBySeller ERROR JSON -> ${ex.localizedMessage}")
            Left(Failure(Throwable()))
        }  catch (e: Exception) {
            Log.d("MYLOG", "getItemsBySeller ERROR EX -> ${e.localizedMessage}")
            Left(Failure(Throwable(e.message.toString())))
        }
    }

}