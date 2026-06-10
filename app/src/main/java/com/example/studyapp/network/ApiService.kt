package com.example.studyapp.network

import com.example.studyapp.domain.model.Book
import com.example.studyapp.domain.model.UserCreateRequest
import com.example.studyapp.domain.model.UserResponse
import com.example.studyapp.domain.model.UserUpdateRequest
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("users")
    suspend fun getAllUsers(): List<UserResponse>

    @GET("user/{id}")
    suspend fun getUserById(@Path("id") id: Long): UserResponse

    @POST("user")
    suspend fun createUser(@Body request: UserCreateRequest): UserResponse

    @PUT("user/{id}")
    suspend fun updateUser(@Path("id") id: Long, @Body request: UserUpdateRequest)

    @DELETE("user/{id}")
    suspend fun deleteuser(@Path("id") id: Long): Response<Unit>

    @GET("users/book")
    suspend fun getBooks(): List<Book>
}