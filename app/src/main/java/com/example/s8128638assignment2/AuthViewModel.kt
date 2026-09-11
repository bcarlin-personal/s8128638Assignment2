package com.example.s8128638assignment2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _loginResult = MutableLiveData<Result<String>>()
    val loginResult: LiveData<Result<String>> = _loginResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _loginResult.value = Result.failure(Exception("Please enter both username and password"))
            return
        }

        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.login(AuthRequest(username, password))
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    val keypass = response.body()!!.keypass
                    _loginResult.value = Result.success(keypass)
                } else {
                    _loginResult.value = Result.failure(Exception("Invalid credentials (${response.code()})"))
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _loginResult.value = Result.failure(Exception("Network error: ${e.localizedMessage}"))
            }
        }
    }
}