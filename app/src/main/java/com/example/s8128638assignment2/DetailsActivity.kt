package com.example.s8128638assignment2

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.s8128638assignment2.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val entity = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("EXTRA_ENTITY", Entity::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("EXTRA_ENTITY") as? Entity
        }

        entity?.let {
            binding.tvDetailProperty1.text = it.property1 ?: "N/A"
            binding.tvDetailProperty2.text = it.property2 ?: "N/A"
            binding.tvDetailDescription.text = it.description ?: "No detailed description available."
        }
    }
}