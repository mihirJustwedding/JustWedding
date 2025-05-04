package com.example.justweddingpro.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.justweddingpro.ClientUi.ClientHomeActivity
import com.example.justweddingpro.ManagerAndCaptainUi.ManagerLoginActivity
import com.example.justweddingpro.R
import com.example.justweddingpro.utils.CommonUtils
import com.example.justweddingpro.utils.Constants
import com.example.justweddingpro.utils.PreferenceManager

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            Redirection()
        }, 3000)
    }

    private fun Redirection() {
        if (PreferenceManager.getPref(Constants.Preference.IS_LOGIN, false) == true) {
            if (CommonUtils.mGetPackageName(this)
                    .contains("com.managerandcaptain.justweddingpro")
            ) {
                /* if (PreferenceManager.getPref(Constants.Preference.Pref_Category, "")
                         .equals("manager")
                 ) {*/
                val accountsIntent = Intent(this, ClientHomeActivity::class.java)
                accountsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                accountsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                accountsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(accountsIntent)
                /*} else {
                    val accountsIntent =
                        Intent(this, OrderHistoryActivity::class.java)
                    accountsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    accountsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    accountsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(accountsIntent)
                    Log.d("MyTAG", "Success")
                }*/
            } else {
                val accountsIntent = Intent(this, MainActivity::class.java)
                accountsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                accountsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                accountsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(accountsIntent)
            }
        } else {
            if (CommonUtils.mGetPackageName(this).contains("com.example.justweddingpro")) {
                if (PreferenceManager.getPref(Constants.Preference.IS_TUTORIAL, true) == true) {
                    val accountsIntent = Intent(this, WelcomeActivity::class.java)
                    accountsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    accountsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    accountsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(accountsIntent)
                } else {
                    val accountsIntent = Intent(this, StartActivity::class.java)
                    accountsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    accountsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    accountsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(accountsIntent)
                }
            } else if (CommonUtils.mGetPackageName(this).contains("com.client.justweddingpro")) {
                val accountsIntent = Intent(this, ClientHomeActivity::class.java)
                accountsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                accountsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                accountsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(accountsIntent)
                PreferenceManager.setPref(Constants.Preference.IS_WRITE_PERMISSION, false)
            } else {
                val accountsIntent = Intent(this, ManagerLoginActivity::class.java)
                accountsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                accountsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                accountsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(accountsIntent)
            }
        }
    }
}