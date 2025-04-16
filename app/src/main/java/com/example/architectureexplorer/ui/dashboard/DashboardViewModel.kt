// ui/dashboard/DashboardViewModel.kt
package com.example.architectureexplorer.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.architectureexplorer.data.model.Entity
import com.example.architectureexplorer.data.remote.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _dashboard = MutableLiveData<Result<List<Entity>>>()
    val dashboard: LiveData<Result<List<Entity>>> = _dashboard

    fun getDashboard(keypass: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getDashboard(keypass)
                _dashboard.value = Result.success(response.entities)
            } catch (e: Exception) {
                _dashboard.value = Result.failure(e)
            }
        }
    }
}