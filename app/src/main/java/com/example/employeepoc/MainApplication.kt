package com.example.employeepoc

import android.app.Application
import com.example.employeepoc.di.modules.remoteModule
import com.example.employeepoc.di.modules.roomModule
import com.example.employeepoc.di.modules.viewModelModule
import org.koin.android.ext.android.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin(
            this,
            listOf(viewModelModule, remoteModule, roomModule)
            , loadPropertiesFromFile = true
        )
    }
}