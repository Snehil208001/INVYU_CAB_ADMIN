package com.tride.admin.data.remote

import com.tride.admin.data.model.AdminLoginRequest
import com.tride.admin.data.model.AdminLoginResponse
import com.tride.admin.data.model.DashboardRequest
import com.tride.admin.data.model.DashboardResponse
import com.tride.admin.data.model.DriverDetailsRequest
import com.tride.admin.data.model.DriverDetailsResponse
import com.tride.admin.data.model.RiderDetailsRequest
import com.tride.admin.data.model.RiderDetailsResponse
import com.tride.admin.data.model.UpdateUserStatusRequest
import com.tride.admin.data.model.UpdateUserStatusResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AdminApiService {

    // Base URL: https://ovlo8ek40d.execute-api.us-east-1.amazonaws.com/riding_app/v1/

    @POST("add_admin")
    suspend fun verifyAdmin(
        @Body request: AdminLoginRequest
    ): Response<AdminLoginResponse>

    @POST("admin_driver_user_rev_details")
    suspend fun getDashboardDetails(
        @Body request: DashboardRequest
    ): Response<DashboardResponse>

    @POST("driver_details_for_admin")
    suspend fun getDriverDetails(
        @Body request: DriverDetailsRequest
    ): Response<DriverDetailsResponse>

    @POST("user_details_from_adminside")
    suspend fun getRiderDetails(
        @Body request: RiderDetailsRequest
    ): Response<RiderDetailsResponse>

    // NEW ENDPOINT
    @POST("admin_update_status_for_user")
    suspend fun updateUserStatus(
        @Body request: UpdateUserStatusRequest
    ): Response<UpdateUserStatusResponse>
}