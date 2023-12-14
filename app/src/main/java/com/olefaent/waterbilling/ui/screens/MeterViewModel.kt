package com.olefaent.waterbilling.ui.screens

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.olefaent.waterbilling.WaterBillingApplication
import com.olefaent.waterbilling.data.WaterBillingRepository
import com.olefaent.waterbilling.model.Meter
import kotlinx.coroutines.launch
import retrofit2.HttpException


class MeterViewModel(private val repository: WaterBillingRepository): ViewModel() {
    var uiState: OneMeterState by mutableStateOf(OneMeterState.Loading)
        private set

    private val _meterID = mutableStateOf(0)
    val meter_id: State<Int> = _meterID

    private val _photo_url = mutableStateOf("")
    val photo_url: State<String> = _photo_url


    init {
        getMeter(meter_id.value)
    }

    fun setMeterId(meterId: Int){
        _meterID.value = meterId
    }
    fun setPhotoUrl(photoUrl: String){
        _photo_url.value = photoUrl
    }


    fun getMeter(meterId: Int){
        viewModelScope.launch {
            try {
                uiState = OneMeterState.Success(repository.getMeter(meterId))
            } catch (e: Exception) {
                uiState = OneMeterState.Error(e.message ?: "Unknown error")
            } catch (e: HttpException) {
                Log.d("Meter state", "getMeter: $meterId")
                uiState = OneMeterState.Error(e.message ?: "Unknown error")
            }

        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WaterBillingApplication)
                val repository = application.container.waterBillingRepository
                MeterViewModel(repository)
            }
        }
    }

}