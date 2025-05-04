package com.example.crmapplication

import android.app.Application
import com.example.justweddingpro.Network.ApiClient
import com.example.justweddingpro.Network.ApiInterface
import com.example.justweddingpro.utils.PreferenceManager

class MyApplication : Application() {

    companion object {
        private var restClient: ApiClient? = null

        fun getRestClient(): ApiInterface? {
            return restClient!!.getApiService()
        }
    }

    override fun onCreate() {
        super.onCreate()
        PreferenceManager.init(applicationContext)
//        FirebaseApp.initializeApp(this)
        restClient = ApiClient(this)
        restClient!!.Client()
    }
}