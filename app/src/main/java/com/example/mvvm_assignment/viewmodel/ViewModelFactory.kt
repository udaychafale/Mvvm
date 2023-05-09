package com.example.mvvm_assignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_assignment.repositories.UserRepository
import com.example.mvvm_assignment.utils.DispatcherProvider

class ViewModelFactory(
    private val apiHelper: UserRepository,
    private val dispatcherProvider: DispatcherProvider
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(apiHelper, dispatcherProvider) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }

}