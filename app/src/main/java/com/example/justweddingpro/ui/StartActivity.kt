package com.example.justweddingpro.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.justweddingpro.R
import com.example.justweddingpro.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_start)
        setContentView(binding.root)

        binding.btnSignin.setOnClickListener {
            startActivity(Intent(this@StartActivity, LoginActivity::class.java))
        }

        binding.btnSignup.setOnClickListener {
            startActivity(Intent(this@StartActivity, SignupActivity::class.java))
        }
    }
}