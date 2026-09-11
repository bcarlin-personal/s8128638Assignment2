package com.example.s8128638assignment2

import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun login(request: AuthRequest): Response<AuthResponse> {
        return apiService.login(request)
    }

    suspend fun getDashboard(keypass: String): Response<DashboardResponse> {
        return apiService.getDashboard(keypass)
    }
}