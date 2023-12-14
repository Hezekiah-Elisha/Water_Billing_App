package com.olefaent.waterbilling.ui.screens

import com.olefaent.waterbilling.model.Meter

sealed interface OneMeterState {
    object Loading: OneMeterState
    data class Success(val meter: Meter) : OneMeterState
    data class Error(val message: String): OneMeterState
}