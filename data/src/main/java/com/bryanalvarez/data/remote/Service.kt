package com.bryanalvarez.data.remote


import android.content.Context
import com.bryanalvarez.data.BuildConfig
import com.bryanalvarez.domain.models.ItemsListInfo
import com.bryanalvarez.domain.models.Category
import com.bryanalvarez.domain.models.SellerInfo
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface Service{

    /**
     * function to make the API call and bring the items by search
     * @param textSearch search typed by the user
     * @param limit max number of items to bring
     * @param offset start number
     */
    @GET("search")
    fun getItemsBySearch(@Query("q") textSearch: String, @Query("limit") limit: Int, @Query("offset") offset: Int): Call<ItemsListInfo>

    /**
     * function to make the API call and bring the list of categories
     */
    @GET("categories")
    fun getCategories(): Call<List<Category>>

    /**
     * function to make the API call and bring the items by category
     * @param categoryId users selected category's id
     * @param limit max number of items to bring
     * @param offset start number
     */
    @GET("search")
    fun getItemsByCategory(@Query("category") categoryId: String, @Query("limit") limit: Int, @Query("offset") offset: Int): Call<ItemsListInfo>

    /**
     * function to make the API call and bring the items by seller
     * @param sellerId users selected seller's id
     */
    @GET("search")
    fun getItemsBySeller(@Query("seller_id") sellerId: String): Call<SellerInfo>

    companion object{

        private var INSTANCE: Service? = null

        /**
         * singleton function to return the Retrofit service instance
         */
        fun getService(context: Context): Service {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            val client = OkHttpClient.Builder().apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                }
                addInterceptor(NetworkConnectionInterceptor(context))
            }.build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.mercadolibre.com/sites/MLC/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(Service::class.java)
        }
    }
}
