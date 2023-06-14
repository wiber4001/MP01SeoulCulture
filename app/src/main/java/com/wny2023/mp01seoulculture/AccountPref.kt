package com.wny2023.mp01seoulculture

import android.app.Application
import com.wny2023.mp01seoulculture.models.Account

class AccountPref:Application() {
    companion object {
        lateinit var prefs: Account
    }

    override fun onCreate() {
        prefs = Account(applicationContext)
        super.onCreate()
    }
}