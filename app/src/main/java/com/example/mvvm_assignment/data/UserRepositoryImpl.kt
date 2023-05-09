package com.example.mvvm_assignment.data

import com.example.mvvm_assignment.api.ApiService
import com.example.mvvm_assignment.repositories.UserRepository
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(private val apiService: ApiService) : UserRepository {

    override fun getUsers() = flow { emit(apiService.getUsers()) }

}