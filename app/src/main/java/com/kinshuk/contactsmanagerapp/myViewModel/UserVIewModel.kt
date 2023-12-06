package com.kinshuk.contactsmanagerapp.myViewModel

import android.widget.Toast
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kinshuk.contactsmanagerapp.MainActivity
import com.kinshuk.contactsmanagerapp.room.User
import com.kinshuk.contactsmanagerapp.room.UserRepo
import kotlinx.coroutines.launch

class UserViewModel(private val repo:UserRepo):ViewModel(),Observable {
    val users = repo.users
    private var isUpdateOrDelete = false
    private lateinit var userToUpdateOrDelete :User

    @Bindable
    val inputName = MutableLiveData<String?>()
    @Bindable
    val inputMail = MutableLiveData<String?>()
    @Bindable
    val inputContact = MutableLiveData<String?>()

    @Bindable
    var saveOrUpdateButtonText = MutableLiveData<String?>()

    @Bindable
    var clearOrDeleteButtonText = MutableLiveData<String?>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearOrDeleteButtonText.value = "Clear All"
    }
    fun sveOrUdate(){
        val name = inputName.value!!
        val email = inputMail.value!!
        val contact = inputContact.value!!
        if(isUpdateOrDelete){
            //make update
            userToUpdateOrDelete.name = inputName.value!!
            userToUpdateOrDelete.email = inputMail.value!!
            userToUpdateOrDelete.contact = inputContact.value!!
            update(userToUpdateOrDelete)
        }
        else
        {
            //insert
            insert(User(0,name,email,contact))
            inputName.value = null
            inputMail.value = null
            inputContact.value = null
        }
    }
    fun clearAllorDelete() {
        if (::userToUpdateOrDelete.isInitialized) {
            // userToUpdateOrDelete is initialized, perform delete
            delete(userToUpdateOrDelete)
        } else {
            // userToUpdateOrDelete is not initialized, perform clearAll
            clearAll()
        }
    }
    fun insert(user:User) = viewModelScope.launch {
        repo.insert(user)
    }
    fun clearAll() = viewModelScope.launch {
        repo.deleteAll()
    }
    fun update(user: User) = viewModelScope.launch {
        repo.update(user)
        //resetting fields
        inputMail.value = null
        inputName.value = null
        inputContact.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearOrDeleteButtonText.value = "Clear All"
    }
    fun delete(user: User) = viewModelScope.launch {
        repo.delete(user)
        //resetting fields
        inputMail.value = null
        inputName.value = null
        inputContact.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearOrDeleteButtonText.value = "Clear All"

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    fun initUpdateAndDelete(user: User) {
        inputMail.value = user.email
        inputName.value = user.name
        inputContact.value = user.contact
        isUpdateOrDelete = true
        userToUpdateOrDelete = user
        saveOrUpdateButtonText.value = "Update"
        clearOrDeleteButtonText.value = "Delete"
    }

    fun reset() {
        inputMail.value = null
        inputName.value = null
        inputContact.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearOrDeleteButtonText.value = "Clear All"
    }

}