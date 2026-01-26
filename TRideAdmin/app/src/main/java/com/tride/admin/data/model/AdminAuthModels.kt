package com.tride.admin.data.model

import com.google.gson.annotations.SerializedName

// Request Body for your Custom API
data class AdminLoginRequest(
    @SerializedName("email")
    val email: String
)

// Response from your Custom API
data class AdminLoginResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<AdminData>?
)

data class AdminData(
    @SerializedName("admin_id")
    val adminId: Int,
    @SerializedName("admin_email")
    val adminEmail: String
)

// --- DASHBOARD MODELS ---
data class DashboardRequest(
    @SerializedName("email")
    val email: String
)

data class DashboardResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("userStatistics")
    val userStatistics: List<UserStat>,
    @SerializedName("totalRevenue")
    val totalRevenue: String
)

data class UserStat(
    @SerializedName("user_role")
    val userRole: String,
    @SerializedName("userCount")
    val userCount: Int
)

// --- DRIVER DETAILS MODELS ---
data class DriverDetailsRequest(
    @SerializedName("email")
    val email: String
)

data class DriverDetailsResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("userStatistics")
    val userStatistics: List<DriverDetailDto>?
)

data class DriverDetailDto(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("full_name")
    val fullName: String?,
    @SerializedName("phone_number")
    val phoneNumber: String?,
    @SerializedName("model")
    val model: String?,
    @SerializedName("vehicle_number")
    val vehicleNumber: String?,
    @SerializedName("rating")
    val rating: Double?,
    @SerializedName("is_verified")
    val isVerified: Int?,
    @SerializedName("status")
    val status: String?
)

// --- RIDER DETAILS MODELS ---
data class RiderDetailsRequest(
    @SerializedName("email")
    val email: String
)

data class RiderDetailsResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("userStatistics")
    val userStatistics: List<RiderDetailDto>?
)

data class RiderDetailDto(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("full_name")
    val fullName: String?,
    @SerializedName("phone_number")
    val phoneNumber: String?,
    @SerializedName("rating")
    val rating: String?,
    @SerializedName("totalCompletedRides")
    val totalCompletedRides: Int?,
    @SerializedName("is_verified")
    val isVerified: Int?,
    @SerializedName("status") // Added this field
    val status: String?
)

// --- UPDATE STATUS MODELS ---
data class UpdateUserStatusRequest(
    @SerializedName("admin_email")
    val adminEmail: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("status")
    val status: String
)

data class UpdateUserStatusResponse(
    @SerializedName("success")
    val success: Boolean
)