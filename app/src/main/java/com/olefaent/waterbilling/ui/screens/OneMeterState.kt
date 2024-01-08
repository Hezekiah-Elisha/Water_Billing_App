package com.olefaent.waterbilling.ui.screens

import com.olefaent.waterbilling.model.Meter

sealed interface OneMeterState {
    /**
     * Loading state:
     * This is the initial state
     */
    object Loading: OneMeterState
    /**
     * Success state:
     * This is the state when the meter is successfully fetched
     */
    data class Success(val meter: Meter) : OneMeterState
    /**
     * Error state:
     * This is the state when an error occurs
     * @param message
     * This is the error message
     * @see OneMeterState
     * @see Loading
     * @see Success
     */
    data class Error(val message: String): OneMeterState
}