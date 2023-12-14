package com.olefaent.waterbilling.data

import okhttp3.Interceptor
import okhttp3.Response

class CustomLoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Log the request
        println("Request: ${request.method} ${request.url}")

        // Proceed with the request and get the response
        val response = chain.proceed(request)

        // Log the response
        println("Response: ${response.code} - ${response.message} - ${response.isSuccessful}")

        return response
    }
}