package com.transfers.transfertracker.network.inteceptors

import android.util.Log
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class HalfHourInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalResponse = chain.proceed(request)

        val cacheControl: CacheControl = CacheControl.Builder()
            .maxAge(30, TimeUnit.MINUTES)
            .build()

        return originalResponse.newBuilder()
            .header(Constants.CACHE_CONTROL_HEADER, cacheControl.toString())
            .build()
    }
}