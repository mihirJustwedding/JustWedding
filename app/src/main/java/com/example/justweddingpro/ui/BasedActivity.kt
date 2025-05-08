package com.example.justweddingpro.ui

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.justweddingpro.R

open class BasedActivity : AppCompatActivity() {
    companion object {
        private var MainThread: Thread? = null

        fun ThreadStop() {
            try {
                MainThread!!.interrupt()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        var mEventId = ""
        var mFunctionId = ""

        var mFunctionName = ""
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = resources.getColor(R.color.toolbarcolor)
        window.navigationBarColor = resources.getColor(R.color.Color_Primery)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("PRT", "onrestart")
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        Log.d(this.packageName, "User interaction to $this")
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        Log.d("PRT", hasFocus.toString())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("PRT", "onActivityResult: ")
    }
}