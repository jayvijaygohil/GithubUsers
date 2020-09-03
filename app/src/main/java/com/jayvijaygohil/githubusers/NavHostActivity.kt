package com.jayvijaygohil.githubusers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jayvijaygohil.githubusers.databinding.ActivityNavHostBinding

class NavHostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNavHostBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}