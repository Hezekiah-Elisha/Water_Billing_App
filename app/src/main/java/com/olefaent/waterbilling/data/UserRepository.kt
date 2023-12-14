package com.olefaent.waterbilling.data

import com.olefaent.waterbilling.model.LoginRequest
import com.olefaent.waterbilling.model.LoginResponse
import com.olefaent.waterbilling.model.UserInfo
import com.olefaent.waterbilling.network.BillingApiService

interface UserRepository{
    suspend fun login(loginRequest: LoginRequest) : LoginResponse
//    fun logout()
//    fun isLoggedIn(): Boolean
//    fun getUserInfo(): UserInfo?
}

class NetworkUserRepository(
    private val userApiService: BillingApiService
): UserRepository {
    override suspend fun login(loginRequest: LoginRequest) : LoginResponse = userApiService.login(loginRequest)
//    fun logout() = userApiService.logout()
//    fun isLoggedIn() = userApiService.isLoggedIn()
//    fun getUserInfo() = userApiService.getUserInfo()
}