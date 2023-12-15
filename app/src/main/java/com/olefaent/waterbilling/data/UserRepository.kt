package com.olefaent.waterbilling.data

import com.olefaent.waterbilling.model.LoginRequest
import com.olefaent.waterbilling.model.LoginResponse
import com.olefaent.waterbilling.model.LogoutResponse
import com.olefaent.waterbilling.network.BillingApiService

interface UserRepository{
    suspend fun login(loginRequest: LoginRequest) : LoginResponse
    suspend fun logout(token: String) : LogoutResponse
//    fun isLoggedIn(): Boolean
//    fun getUserInfo(): UserInfo?
}

class NetworkUserRepository(
    private val userApiService: BillingApiService
): UserRepository {
    override suspend fun login(loginRequest: LoginRequest) : LoginResponse = userApiService.login(loginRequest)
    override suspend fun logout(token: String): LogoutResponse = userApiService.logout("Bearer $token")
//    fun logout() = userApiService.logout()
//    fun isLoggedIn() = userApiService.isLoggedIn()
//    fun getUserInfo() = userApiService.getUserInfo()
}