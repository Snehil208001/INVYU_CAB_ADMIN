package com.tride.admin.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.tride.admin.core.utils.Resource
import com.tride.admin.data.model.AdminData
import com.tride.admin.data.model.AdminLoginRequest
import com.tride.admin.data.remote.AdminApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: AdminApiService,
    private val firebaseAuth: FirebaseAuth
) {

    suspend fun loginAdmin(email: String, pass: String): Resource<AdminData> {
        return withContext(Dispatchers.IO) {
            try {
                // 1. Firebase Auth
                val firebaseResult = firebaseAuth.signInWithEmailAndPassword(email, pass).await()

                if (firebaseResult.user == null) {
                    return@withContext Resource.Error("Firebase authentication failed")
                }

                // 2. Custom API Call
                val apiResponse = apiService.verifyAdmin(AdminLoginRequest(email))

                // --- LOGGING START ---
                if (apiResponse.body() != null) {
                    // Convert the object back to JSON string to verify in Logcat
                    val jsonResponse = Gson().toJson(apiResponse.body())
                    Log.d("API_DEBUG", "Raw Response: $jsonResponse")
                } else {
                    Log.e("API_DEBUG", "Response Body was NULL. Code: ${apiResponse.code()}")
                }
                // --- LOGGING END ---

                if (apiResponse.isSuccessful && apiResponse.body() != null) {
                    val responseBody = apiResponse.body()!!

                    if (responseBody.success && !responseBody.data.isNullOrEmpty()) {
                        Log.d("API_DEBUG", "Login Success! Admin ID: ${responseBody.data[0].adminId}")
                        Resource.Success(responseBody.data[0])
                    } else {
                        Log.e("API_DEBUG", "Login Failed: ${responseBody.message}")
                        Resource.Error(responseBody.message)
                    }
                } else {
                    Log.e("API_DEBUG", "Server Error: ${apiResponse.code()}")
                    Resource.Error("Server error: ${apiResponse.code()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("API_DEBUG", "Exception: ${e.localizedMessage}")
                Resource.Error(e.localizedMessage ?: "An unknown error occurred")
            }
        }
    }
}