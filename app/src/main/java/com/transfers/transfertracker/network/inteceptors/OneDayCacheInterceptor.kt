package com.transfers.transfertracker.network.inteceptors

import android.util.Log
import com.transfers.transfertracker.network.inteceptors.Constants.CACHE_CONTROL_HEADER
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class OneDayCacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalResponse = chain.proceed(request)

        val cacheControl: CacheControl = CacheControl.Builder()
            .maxAge(1, TimeUnit.DAYS)
            .build()

        return originalResponse.newBuilder()
            .header(CACHE_CONTROL_HEADER, cacheControl.toString())
            .build()
    }
}