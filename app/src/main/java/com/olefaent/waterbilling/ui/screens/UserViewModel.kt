package com.olefaent.waterbilling.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.olefaent.waterbilling.WaterBillingApplication
import com.olefaent.waterbilling.data.UserRepository
import com.olefaent.waterbilling.model.LoginRequest
import com.olefaent.waterbilling.model.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.HttpException

sealed interface UserState{
    object Loading : UserState
    data class Success(val loginResponse: LoginResponse): UserState
    data class Error(val message: String) : UserState
}

class UserViewModel(private val repository: UserRepository): ViewModel(){
//    fun login(username: String, password: String) = repository.login(username)
//    fun logout() = repository.logout()
//    fun isLoggedIn() = repository.isLoggedIn()
//    fun getUserInfo() = repository.getUserInfo()

    var uiState: UserState by mutableStateOf(UserState.Loading)
        private set

    private val _email = mutableStateOf("")
    val email: String = _email.value

    private val _password = mutableStateOf("")
    val password: String = _password.value

    fun setEmail(email: String){
        _email.value = email
    }
    fun setPassword(password: String){
        _password.value = password
    }

    init {
        login(
            email = email,
            password = password
        )
    }

    fun login(email: String, password: String){
        val loginRequest = LoginRequest(email, password)
        viewModelScope.launch {
            uiState = try{
                UserState.Success(repository.login(loginRequest))
            } catch (e: Exception){
                UserState.Error(e.message ?: "Unknown error")
            } catch (e: HttpException){
                UserState.Error(e.message ?: "Unknown error")
//                UserState.Error("Invalid email or password")
            
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as WaterBillingApplication)
                val userRepository = application.container.userRepository
                UserViewModel(userRepository)
            }
        }
    }
}