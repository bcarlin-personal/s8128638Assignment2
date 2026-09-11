package com.example.s8128638assignment2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _entities = MutableLiveData<Result<List<Entity>>>()
    val entities: LiveData<Result<List<Entity>>> = _entities

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchDashboard(keypass: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getDashboard(keypass)
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _entities.value = Result.success(response.body()!!.entities)
                } else {
                    _entities.value = Result.failure(Exception("Failed to fetch dashboard (${response.code()})"))
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _entities.value = Result.failure(Exception("Network error: ${e.localizedMessage}"))
            }
        }
    }
}