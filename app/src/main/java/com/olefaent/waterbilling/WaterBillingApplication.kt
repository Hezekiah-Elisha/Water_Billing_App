package com.olefaent.waterbilling

import android.app.Application
import com.olefaent.waterbilling.data.AppContainer
import com.olefaent.waterbilling.data.DefaultAppContainer

class WaterBillingApplication: Application() {
lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()

        container = DefaultAppContainer()
    }
}