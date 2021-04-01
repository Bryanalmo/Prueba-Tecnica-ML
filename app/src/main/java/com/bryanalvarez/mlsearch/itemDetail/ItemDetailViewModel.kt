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

    fun getItemsBySeller(item: Item): LiveData<List<Item>> {
        itemSelected = item
        notifyChange()
        itemsList = MutableLiveData()
        getItemsBySellerData()
        return itemsList
    }

    private fun getItemsBySellerData() {
        val params = GetItemsBySeller.Params(itemSelected.seller?.id)
        getItemsBySeller.execute(params){either ->
            either.fold(
                {
                    Log.d("MYLOG ERROR", "error -> ${it.exception.localizedMessage}")
                },{
                    Log.d("MYLOG", "items by seller -> $it")
                    seller = it.seller
                    itemsList.postValue(it.results.subList(0, 3))
                    notifyChange()
                }
            )
        }
    }
}