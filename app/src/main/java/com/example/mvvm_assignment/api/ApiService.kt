package com.example.mvvm_assignment.api

import com.example.mvvm_assignment.model.User
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>
}