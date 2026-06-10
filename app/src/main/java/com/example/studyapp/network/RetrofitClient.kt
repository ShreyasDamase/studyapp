package com.example.studyapp.network

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RequiresApi(Build.VERSION_CODES.O)
object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/api/"

    @RequiresApi(Build.VERSION_CODES.O)
    private val gson = GsonBuilder().registerTypeAdapter(
        LocalDateTime::class.java,
        LocalDateTimeAdapter()
    ).setLenient().create()

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).connectTimeout(
            30,
            TimeUnit.SECONDS
        ).readTimeout(30, TimeUnit.SECONDS).build()

    val apiService: ApiService by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient).addConverterFactory(
            GsonConverterFactory.create(gson)
        ).build().create(ApiService::class.java)
    }
}