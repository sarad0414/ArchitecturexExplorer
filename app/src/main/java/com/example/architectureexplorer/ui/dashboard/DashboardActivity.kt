// ui/dashboard/DashboardActivity.kt
package com.example.architectureexplorer.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.architectureexplorer.databinding.ActivityDashboardBinding
import com.example.architectureexplorer.ui.details.DetailsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var adapter: DashboardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = DashboardAdapter { entity ->
            val intent = Intent(this, DetailsActivity::class.java).apply {
                putExtra("entity", entity)
            }
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        val keypass = intent.getStringExtra("KEYPASS")
        if (keypass.isNullOrEmpty()) {
            binding.tvError.text = "Missing keypass. Please login again."
            binding.tvError.visibility = View.VISIBLE
            return
        }

        viewModel.getDashboard(keypass)

        viewModel.dashboard.observe(this) { result ->
            result.onSuccess { entities ->
                adapter.submitList(entities)
                binding.tvError.visibility = View.GONE
            }

            result.onFailure { error ->
                binding.tvError.text = "Error: ${error.message}"
                binding.tvError.visibility = View.VISIBLE
            }
        }
    }
}