package com.tride.admin.data.repository

import android.util.Log
import com.tride.admin.core.utils.Resource
import com.tride.admin.data.model.DriverDetailsRequest
import com.tride.admin.data.model.DriverDetailsResponse
import com.tride.admin.data.remote.AdminApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ManageDriversRepository @Inject constructor(
    private val apiService: AdminApiService
) {
    suspend fun getDrivers(email: String): Resource<DriverDetailsResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getDriverDetails(DriverDetailsRequest(email))

                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    if (body.success) {
                        Resource.Success(body)
                    } else {
                        Resource.Error("Failed to fetch drivers: Success false")
                    }
                } else {
                    Resource.Error("Server error: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("ManageDriversRepo", "Error: ${e.localizedMessage}")
                Resource.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}