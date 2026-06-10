package com.example.studyapp.domain.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class UserResponse(
    val id: Long,
    val name: String,
    val email: String,
    @SerializedName("createdAt")
    val createdAt: LocalDateTime,
    @SerializedName("updatedAt")
    val updatedAt: LocalDateTime

)
