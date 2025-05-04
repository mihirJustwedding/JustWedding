package com.example.justweddingpro.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.justweddingpro.R
import com.example.justweddingpro.databinding.ActivityEnterOtpScreenBinding

class EnterOtpScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEnterOtpScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_enter_otp_screen)
        setContentView(binding.root)

    }
}