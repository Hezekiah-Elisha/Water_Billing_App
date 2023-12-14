package com.olefaent.waterbilling.ui.screens

import com.olefaent.waterbilling.model.Meter

sealed interface MeterState {
    object Loading: MeterState
    data class Success(val meters: List<Meter>): MeterState
    data class Error(val message: String): MeterState
}