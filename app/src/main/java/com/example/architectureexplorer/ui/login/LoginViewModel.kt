// ui/login/LoginViewModel.kt
package com.example.architectureexplorer.ui.login

import androidx.lifecycle.*
import com.example.architectureexplorer.data.remote.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _loginResult = MutableLiveData<Result<String>>()
    val loginResult: LiveData<Result<String>> = _loginResult

    fun login(campus: String, username: String, password: String) {
        viewModelScope.launch {
            try {
                val credentials = mapOf("username" to username, "password" to password)
                val response = apiService.login(campus, credentials)
                _loginResult.postValue(Result.success(response.keypass))
            } catch (e: Exception) {
                _loginResult.postValue(Result.failure(e))
            }
        }
    }
}
