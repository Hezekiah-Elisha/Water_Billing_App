package com.olefaent.waterbilling.data

import com.olefaent.waterbilling.data.WaterBillingRepository
import com.olefaent.waterbilling.model.Customer
import com.olefaent.waterbilling.model.LoginRequest
import com.olefaent.waterbilling.model.LoginResponse
import com.olefaent.waterbilling.model.Meter
import com.olefaent.waterbilling.network.BillingApiService

interface WaterBillingRepository{
    /**
     * Returns a list of all customers
     */
    suspend fun getCustomers(): List<Customer>

    /**
     * Returns a list of all meters
     */
    suspend fun getMeters(): List<Meter>

    /**
     * Returns a meter with the given id
     */
    suspend fun getMeter(meterId: Int): Meter

    /**
     * Returns a access_token for the given username and password
     */
    suspend fun login(loginRequest: LoginRequest): LoginResponse

}

class NetworkWaterBillingRepository(
    private val billingApiService: BillingApiService
): WaterBillingRepository {
    override suspend fun getCustomers(): List<Customer> = billingApiService.getCustomers()
    override suspend fun getMeters(): List<Meter> = billingApiService.getMeters()
    override suspend fun getMeter(meterId: Int): Meter = billingApiService.getMeter(meterId)
    override suspend fun login(loginRequest: LoginRequest): LoginResponse = billingApiService.login(loginRequest)
}

