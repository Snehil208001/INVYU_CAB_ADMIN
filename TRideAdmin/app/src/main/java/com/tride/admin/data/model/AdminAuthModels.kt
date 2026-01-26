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