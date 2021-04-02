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

    @GET("search")
    fun getItemsBySearch(@Query("q") textSearch: String, @Query("limit") limit: Int, @Query("offset") offset: Int): Call<ItemsListInfo>

    @GET("categories")
    fun getCategories(): Call<List<Category>>

    @GET("search")
    fun getItemsByCategory(@Query("category") categoryId: String, @Query("limit") limit: Int, @Query("offset") offset: Int): Call<ItemsListInfo>

    @GET("search")
    fun getItemsBySeller(@Query("seller_id") sellerId: String): Call<SellerInfo>

    companion object{
        fun getService(context: Context): Service {

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
