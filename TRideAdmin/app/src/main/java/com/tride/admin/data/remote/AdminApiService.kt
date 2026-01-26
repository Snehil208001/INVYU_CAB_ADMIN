package com.tride.admin.data.remote

import com.tride.admin.data.model.AdminLoginRequest
import com.tride.admin.data.model.AdminLoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AdminApiService {

    // Base URL should be: https://ovlo8ek40d.execute-api.us-east-1.amazonaws.com/riding_app/v1/

    @POST("add_admin")
    suspend fun verifyAdmin(
        @Body request: AdminLoginRequest
    ): Response<AdminLoginResponse>
}