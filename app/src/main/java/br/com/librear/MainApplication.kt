package br.com.librear

import android.app.Application


class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        RetrofitInstance.initialize(this)
    }
}