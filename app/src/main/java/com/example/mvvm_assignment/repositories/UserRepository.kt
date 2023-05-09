package com.example.mvvm_assignment.repositories

import com.example.mvvm_assignment.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUsers(): Flow<List<User>>

}