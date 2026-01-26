package com.tride.admin.data.repository

import android.util.Log
import com.tride.admin.core.utils.Resource
import com.tride.admin.data.model.DashboardRequest
import com.tride.admin.data.model.DashboardResponse
import com.tride.admin.data.remote.AdminApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DashboardRepository @Inject constructor(
    private val apiService: AdminApiService
) {

    suspend fun getDashboardDetails(email: String): Resource<DashboardResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getDashboardDetails(DashboardRequest(email))

                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    if (body.success) {
                        Resource.Success(body)
                    } else {
                        Resource.Error("Failed to fetch dashboard data")
                    }
                } else {
                    Resource.Error("Server error: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("DashboardRepository", "Error: ${e.localizedMessage}")
                Resource.Error(e.localizedMessage ?: "An unknown error occurred")
            }
        }
    }
}