package com.example.architectureexplorer.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.architectureexplorer.databinding.ActivityLoginBinding
import com.example.architectureexplorer.ui.dashboard.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private val CAMPUS = "sydney"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                binding.tvError.text = "Please enter all fields"
                binding.tvError.visibility = View.VISIBLE
            } else {
                viewModel.login(CAMPUS, username, password)
            }
        }

        viewModel.loginResult.observe(this) { result ->
            result.onSuccess { keypass ->
                val intent = Intent(this, DashboardActivity::class.java).apply {
                    putExtra("KEYPASS", keypass)
                }
                startActivity(intent)
                finish()
            }

            result.onFailure {
                binding.tvError.text = "Login failed: ${it.message}"
                binding.tvError.visibility = View.VISIBLE
            }
        }
    }
}
