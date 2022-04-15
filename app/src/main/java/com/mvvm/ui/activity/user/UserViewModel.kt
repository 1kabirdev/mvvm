package com.mvvm.ui.activity.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mvvm.data.model.User
import com.mvvm.data.repository.UserRepository
import kotlinx.coroutines.*

class UserViewModel(val mainRepository: UserRepository) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val user = MutableLiveData<User>()
    var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getUser(login: String) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = mainRepository.getUser(login)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    user.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}