package com.olefaent.waterbilling.network

import com.olefaent.waterbilling.model.Customer
import com.olefaent.waterbilling.model.LoginRequest
import com.olefaent.waterbilling.model.LoginResponse
import com.olefaent.waterbilling.model.Meter
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BillingApiService {
    @GET("/customers")
    suspend fun getCustomers(): List<Customer>

    @GET("/meters")
    suspend fun getMeters(): List<Meter>

    @GET("/meters/{meterId}")
    suspend fun getMeter(@Path("meterId") meterId: Int): Meter

    @GET("/customers/{customerId}")
    suspend fun getCustomer(@Path("customerId") customerId: Int): Customer

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
}