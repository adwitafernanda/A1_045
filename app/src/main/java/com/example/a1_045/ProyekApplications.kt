package com.example.a1_045

import android.app.Application
import com.example.a1_045.dependenciesinjection.AppContainerProyek
import com.example.a1_045.dependenciesinjection.ProyekContainer

class ProyekApplications: Application() {
    lateinit var containerproyek: AppContainerProyek
    override fun onCreate() {
        super.onCreate()
        containerproyek = ProyekContainer()
    }
}