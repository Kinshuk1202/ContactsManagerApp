package com.kinshuk.contactsmanagerapp.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kinshuk.contactsmanagerapp.myViewModel.UserViewModel

class VmFactory(private val repo:UserRepo):ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}