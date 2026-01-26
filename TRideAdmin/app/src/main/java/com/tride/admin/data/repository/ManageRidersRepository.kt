package com.tride.admin.data.repository

import android.util.Log
import com.tride.admin.core.utils.Resource
import com.tride.admin.data.model.RiderDetailsRequest
import com.tride.admin.data.model.RiderDetailsResponse
import com.tride.admin.data.model.UpdateUserStatusRequest
import com.tride.admin.data.model.UpdateUserStatusResponse
import com.tride.admin.data.remote.AdminApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ManageRidersRepository @Inject constructor(
    private val apiService: AdminApiService
) {
    suspend fun getRiders(email: String): Resource<RiderDetailsResponse> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("ManageRidersRepo", "Fetching riders for email: $email") // Log Request
                val response = apiService.getRiderDetails(RiderDetailsRequest(email))

                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    if (body.success) {
                        Log.d("ManageRidersRepo", "Riders fetched successfully. Count: ${body.userStatistics?.size}") // Log Success
                        Resource.Success(body)
                    } else {
                        Log.e("ManageRidersRepo", "Failed to fetch riders: Success flag is false") // Log Failure
                        Resource.Error("Failed to fetch riders: Success false")
                    }
                } else {
                    Log.e("ManageRidersRepo", "Server error fetching riders: ${response.code()}") // Log Error
                    Resource.Error("Server error: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("ManageRidersRepo", "Exception fetching riders: ${e.localizedMessage}") // Log Exception
                Resource.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    suspend fun updateUserStatus(adminEmail: String, userId: Int, status: String): Resource<UpdateUserStatusResponse> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("ManageRidersRepo", "Updating status for UserID: $userId to '$status' by Admin: $adminEmail") // Log Request
                val request = UpdateUserStatusRequest(adminEmail, userId, status)
                val response = apiService.updateUserStatus(request)

                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    if (body.success) {
                        Log.d("ManageRidersRepo", "Status update successful for UserID: $userId") // Log Success
                        Resource.Success(body)
                    } else {
                        Log.e("ManageRidersRepo", "Status update failed for UserID: $userId. Success flag is false.") // Log Failure
                        Resource.Error("Failed to update status")
                    }
                } else {
                    Log.e("ManageRidersRepo", "Server error updating status: ${response.code()}") // Log Error
                    Resource.Error("Server error: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("ManageRidersRepo", "Exception updating status: ${e.localizedMessage}") // Log Exception
                Log.e("ManageRidersRepo", "Update Error: ${e.localizedMessage}")
                Resource.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}