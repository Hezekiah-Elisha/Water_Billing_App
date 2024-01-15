package com.olefaent.waterbilling.model

import android.graphics.Bitmap

data class Reading (
    val meterId: Int,
    val workerId: Int,
    val readingGpsCoordinates: String,
    val meterStatus: String,
    val readingImage: String,
    val readingDate: String,
    val readingValue: Int,
    val comments : String
)