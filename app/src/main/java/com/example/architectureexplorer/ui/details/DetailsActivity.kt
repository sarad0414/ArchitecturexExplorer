// ui/details/DetailsActivity.kt
package com.example.architectureexplorer.ui.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.architectureexplorer.data.model.Entity
import com.example.architectureexplorer.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val entity = intent.getSerializableExtra("entity") as? Entity

        if (entity == null) {
            binding.tvName.text = "Entity not found."
            return
        }

        binding.tvName.text = entity.name
        binding.tvArchitect.text = "Architect: ${entity.architect}"
        binding.tvLocation.text = "Location: ${entity.location}"
        binding.tvYear.text = "Completed: ${entity.yearCompleted}"
        binding.tvStyle.text = "Style: ${entity.style}"
        binding.tvHeight.text = "Height: ${entity.height} m"
        binding.tvDescription.text = entity.description
    }
}