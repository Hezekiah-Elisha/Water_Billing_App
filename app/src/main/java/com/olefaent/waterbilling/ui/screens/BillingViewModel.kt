package com.olefaent.waterbilling.ui.screens

import android.util.Log
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
import com.olefaent.waterbilling.data.WaterBillingRepository
import com.olefaent.waterbilling.model.Customer
import kotlinx.coroutines.launch
import retrofit2.HttpException

sealed interface BillingState {
    object Loading : BillingState
    data class Success(val customers: List<Customer>) : BillingState
    data class Error(val message: String) : BillingState
}
//sealed interface MeterState {
//    object Loading : MeterState
//    data class Success(val meters: List<Meter>) : MeterState
//    data class Error(val message: String) : MeterState
//}


class BillingViewModel(private val billingRepository: WaterBillingRepository) : ViewModel(){
    var uiState: BillingState by mutableStateOf(BillingState.Loading)
        private set

    var meterState: MeterState by mutableStateOf(MeterState.Loading)
        private set

    var oneMeterState: OneMeterState by mutableStateOf(OneMeterState.Loading)
        private set

    init {
        getCustomers()
        getMeters()
        getMeter(1)
    }

    private fun getCustomers(){
        viewModelScope.launch {
            uiState = try {
                BillingState.Success(billingRepository.getCustomers())
            } catch (e: Exception){
                BillingState.Error(e.message ?: "Unknown error")
            } catch (e: HttpException){
                BillingState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getMeters(){
        viewModelScope.launch {
            meterState = try {
                MeterState.Success(billingRepository.getMeters())
            } catch (e: Exception){
                MeterState.Error(e.message ?: "Unknown error")
            } catch (e: HttpException){
                MeterState.Error(e.message ?: "Unknown error")
            }
            Log.d("Meters State", "getMeters: $meterState")
        }
    }

    fun getMeter(meterId: Int){
        viewModelScope.launch {
            oneMeterState = try {
                OneMeterState.Success(billingRepository.getMeter(meterId))
            } catch (e: Exception){
                OneMeterState.Error(e.message ?: "Unknown error")
            } catch (e: HttpException){
                OneMeterState.Error(e.message ?: "Unknown error")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as WaterBillingApplication)
                val repository = application.container.waterBillingRepository
                BillingViewModel(repository)
            }
        }
    }
}