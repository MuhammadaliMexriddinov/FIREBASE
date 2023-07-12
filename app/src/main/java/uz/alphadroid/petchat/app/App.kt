package uz.alphadroid.petchat.app


import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: App
        @SuppressLint("StaticFieldLeak")
        lateinit var activity:Activity
    }

    override fun onCreate() {
        super.onCreate()
        instance=this
    }
}
