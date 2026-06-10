package com.example.studyapp.domain.model

data class UserUpdateRequest(
    val name: String? = null,
    val email: String? = null,
    val password: String? = null
)