package com.bryanalvarez.mlsearch.itemDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bryanalvarez.domain.interactors.GetItemsBySeller
import com.bryanalvarez.domain.models.Item
import com.bryanalvarez.domain.models.Seller
import com.bryanalvarez.mlsearch.core.ObservableViewModel

class ItemDetailViewModel(private val getItemsBySeller: GetItemsBySeller): ObservableViewModel() {

    private lateinit var itemsList: MutableLiveData<List<Item>>
    var itemSelected = Item()
    var seller: Seller = Seller()
    var itemBySellerError = MutableLiveData<String>()
    var loadingItemsList = false

    /**
     * function to instantiate the itemsList and returns it to be observe
     * @param item item selected by the user
     */
    fun getItemsBySeller(item: Item): LiveData<List<Item>> {
        if(!::itemsList.isInitialized){
            itemSelected = item
            notifyChange()
            itemsList = MutableLiveData()
            getItemsBySellerData()
        }
        return itemsList
    }

    /**
     * function to execute the GetItemsBySeller interactor to bring the items list
     */
    private fun getItemsBySellerData() {
        loadingItemsList = true
        notifyChange()
        val params = GetItemsBySeller.Params(itemSelected.seller?.idSeller)
        getItemsBySeller.execute(params){either ->
            either.fold(
                {
                    Log.d("MYLOG ERROR", "error -> ${it.exception.localizedMessage}")
                    itemBySellerError.postValue(it.exception.localizedMessage)
                    loadingItemsList = false
                    notifyChange()
                },{
                    Log.d("MYLOG", "items by seller -> $it")
                    seller = it.seller
                    try{
                        itemsList.postValue(it.results.subList(0, 3))
                    }catch(e: Exception){
                        itemsList.postValue(it.results)
                    }
                    loadingItemsList = false
                    notifyChange()
                }
            )
        }
    }
}