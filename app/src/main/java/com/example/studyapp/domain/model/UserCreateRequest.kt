package com.example.studyapp.domain.model

data class UserCreateRequest(
    val name: String,
    val email: String,
    val password: String

)