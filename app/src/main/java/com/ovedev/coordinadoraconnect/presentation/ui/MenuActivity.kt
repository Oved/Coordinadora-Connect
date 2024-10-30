package com.ovedev.coordinadoraconnect.presentation.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ovedev.coordinadoraconnect.R
import com.ovedev.coordinadoraconnect.databinding.ActivityMenuBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}