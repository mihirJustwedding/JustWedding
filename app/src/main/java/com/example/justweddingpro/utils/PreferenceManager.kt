package com.example.justweddingpro.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.preference.PreferenceManager
import androidx.annotation.RequiresApi
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import java.io.IOException
import java.security.GeneralSecurityException

object PreferenceManager {
    private var mSharedPreferences: SharedPreferences? = null
    private var mEditor: SharedPreferences.Editor? = null

    fun init(context: Context?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mSharedPreferences = getEncryptedSharedPreferences(context)
        } else mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    fun getEncryptedSharedPreferences(context: Context?): SharedPreferences? {
        val masterKeyAlias: String
        var sharedPreferences: SharedPreferences? = null
        try {
            masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

            sharedPreferences = EncryptedSharedPreferences.create(
                    "secret_shared_prefs_file",
                    masterKeyAlias,
                    context!!,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (e: GeneralSecurityException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return sharedPreferences
    }

    fun clearAll() {
        val token = getPref(Constants.Preference.BEARER_TOKEN, "")
        mSharedPreferences!!.edit().clear().apply()
        mSharedPreferences!!.edit().apply()
        setPref(Constants.Preference.BEARER_TOKEN, token!!)
    }

    operator fun contains(key: String?): Boolean {
        return mSharedPreferences!!.contains(key)
    }

    fun removePrefSingle(keyToRemove: String?) {
//        mSharedPreferences = context.getSharedPreferences(SHAREDPREFERENCE, Context.MODE_PRIVATE)
        mEditor = mSharedPreferences?.edit()
        mEditor?.remove(keyToRemove)
        mEditor?.apply()
    }

    fun removePref() {
//        mSharedPreferences = context.getSharedPreferences(SHAREDPREFERENCE, Context.MODE_PRIVATE)
        mEditor = mSharedPreferences!!.edit().clear()
        mEditor?.apply()
    }

    fun setPref(key: String, value: String) {
//        mSharedPreferences = context.getSharedPreferences(SHAREDPREFERENCE, Context.MODE_PRIVATE)
        mEditor = mSharedPreferences?.edit()
        mEditor?.putString(key, value)
        mEditor?.apply()
    }

    fun getPref(key: String, defaultvalue: String): String? {
//        mSharedPreferences = context.getSharedPreferences(SHAREDPREFERENCE, Context.MODE_PRIVATE)
        return mSharedPreferences?.getString(key, defaultvalue)
    }


    fun setPref(key: String, value: Int) {
//        mSharedPreferences = context.getSharedPreferences(SHAREDPREFERENCE, Context.MODE_PRIVATE)
        mEditor = mSharedPreferences?.edit()
        mEditor?.putInt(key, value)
        mEditor?.apply()
    }

    fun getPref(key: String, defaultvalue: Int): Int? {
//        mSharedPreferences = context.getSharedPreferences(SHAREDPREFERENCE, Context.MODE_PRIVATE)
        return mSharedPreferences?.getInt(key, defaultvalue)
    }

    fun setPref(key: String, value: Long) {
//        mSharedPreferences = context.getSharedPreferences(SHAREDPREFERENCE, Context.MODE_PRIVATE)
        mEditor = mSharedPreferences?.edit()
        mEditor?.putLong(key, value)
        mEditor?.apply()
    }

    fun getPref(key: String, defaultvalue: Long): Long? {
//        mSharedPreferences = context.getSharedPreferences(SHAREDPREFERENCE, Context.MODE_PRIVATE)
        return mSharedPreferences?.getLong(key, defaultvalue)
    }

    fun setPref(key: String, value: Boolean) {
//        mSharedPreferences = context.getSharedPreferences(SHAREDPREFERENCE, Context.MODE_PRIVATE)
        mEditor = mSharedPreferences?.edit()
        mEditor?.putBoolean(key, value)
        mEditor?.apply()
    }

    fun getPref(key: String, defaultvalue: Boolean): Boolean? {
//        mSharedPreferences = context.getSharedPreferences(SHAREDPREFERENCE, Context.MODE_PRIVATE)
        return mSharedPreferences?.getBoolean(key, defaultvalue)
    }

}