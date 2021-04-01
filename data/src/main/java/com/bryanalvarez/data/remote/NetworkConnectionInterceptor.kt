package com.bryanalvarez.data.remote

import android.content.Context
import android.net.ConnectivityManager
import com.bryanalvarez.data.exceptions.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class NetworkConnectionInterceptor(context: Context) : Interceptor {
    private val mContext: Context = context

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected) {
            throw NoConnectivityException("Parece que no tienes conexión a internet")
        }
        val builder: Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

    private val isConnected: Boolean
        get() {
            val connectivityManager =
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = connectivityManager.activeNetworkInfo
            return netInfo != null && netInfo.isConnected
        }

}