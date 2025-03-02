package com.pose_estimation

import android.app.Application

class Application: Application() {
    override fun onCreate() {
        super.onCreate()
        initDependencyInjection()
    }
}