package com.example.justweddingpro.ClientUi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.justweddingpro.databinding.ActivityClientLoginScreenBinding

class ClientLoginScreen : AppCompatActivity() {

    private lateinit var binding: ActivityClientLoginScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}