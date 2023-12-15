package com.olefaent.waterbilling.ui.screens

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import com.olefaent.waterbilling.model.User
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface UserState{
    object Loading : UserState
    data class Success(val loginResponse: LoginResponse): UserState
    data class Error(val message: String) : UserState
}

class UserViewModel(private val repository: UserRepository, private val context: Context): ViewModel(){
//    fun login(username: String, password: String) = repository.login(username)
//    fun logout() = repository.logout()
//    fun isLoggedIn() = repository.isLoggedIn()
//    fun getUserInfo() = repository.getUserInfo()

    private val sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

    var uiState: UserState by mutableStateOf(UserState.Loading)
        private set

    private val _email = MutableLiveData("")
    val email : String = _email.value ?: "NoneEmail"

    private val _password = MutableLiveData("")
    val password : String = _password.value ?: "NonePassword"

    fun setEmail(email: String){
        _email.value = email
    }
    fun setPassword(password: String){
        _password.value = password
    }

    fun getThePassword(): String{
        return password
    }

    fun getTheEmail(): String{
        return email
    }

//    init {
//        login(
//            email = email,
//            password = password
//        )
//    }

    fun loginUser(email: String, password: String){
        val loginRequest = LoginRequest(email, password)

        Log.d("Login", "login: $loginRequest, $email, $password")
        viewModelScope.launch {
            uiState = try{
                UserState.Success(repository.login(loginRequest))
            } catch (e: Exception){
                UserState.Error(e.message ?: "Unknown error")
//                UserState.Error("Invalid email or password")
            } catch (e: HttpException){
                UserState.Error(e.message ?: "Unknown error")
//                UserState.Error("Invalid email or password")
            } catch (e: IOException) {
                UserState.Error("Could not reach the server, please check your internet connection!")
            }
        }
    }

    fun saveAccessToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString("access_token", token)
        editor.apply()
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString("access_token", null)
    }

    fun clearAccessToken() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return getAccessToken() != null
    }

    fun logout() {
        viewModelScope.launch {
            val token = getAccessToken()
            repository.logout(token.toString())
        }
        clearAccessToken()
    }

    fun setUserLoggedIn(user: User){
        val editor = sharedPreferences.edit()
        editor.putInt("user_id", user.id)
        editor.putString("user_username", user.username)
        editor.putString("user_email", user.email)
        editor.putString("user_role", user.role)
        editor.putString("user_created_at", user.created_at)
        editor.apply()
    }

    fun getUserLoggedIn(): User? {
        val id = sharedPreferences.getInt("user_id", 0)
        val username = sharedPreferences.getString("user_username", null)
        val email = sharedPreferences.getString("user_email", null)
        val role = sharedPreferences.getString("user_role", null)
        val created_at = sharedPreferences.getString("user_created_at", null)

        return if (id != 0 && username != null && email != null && role != null && created_at != null) {
            User(id, username, email, role, created_at)
        } else {
            null
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as WaterBillingApplication)
                val userRepository = application.container.userRepository
                UserViewModel(userRepository, application.applicationContext)
            }
        }
    }
}