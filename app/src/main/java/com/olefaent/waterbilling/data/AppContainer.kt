package com.olefaent.waterbilling.data

import com.olefaent.waterbilling.network.BillingApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface AppContainer{
    val waterBillingRepository: WaterBillingRepository
    val userRepository: UserRepository
}

class DefaultAppContainer: AppContainer{
    private val base_url = "https://hezekiahelisha.pythonanywhere.com/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

//    val client = OkHttpClient.Builder()
//        .addInterceptor { chain ->
//            val newRequest = chain.request().newBuilder()
//                .addHeader("Authorization", "Bearer ${BillingApiService.token}")
//                .build()
//            chain.proceed(newRequest)
//        }
//        .build()
    val loggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    val client = OkHttpClient.Builder()
        .addInterceptor(
            CustomLoggingInterceptor()
        )
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(base_url)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    private val retrofitService: BillingApiService by lazy {
        retrofit.create(BillingApiService::class.java)
    }

    override val waterBillingRepository: WaterBillingRepository by lazy {
        NetworkWaterBillingRepository(retrofitService)
    }

    override val userRepository: UserRepository by lazy {
        NetworkUserRepository(retrofitService)
    }
}