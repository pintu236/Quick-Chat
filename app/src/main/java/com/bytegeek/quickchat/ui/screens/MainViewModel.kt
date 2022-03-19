package com.bytegeek.quickchat.ui.screens

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bytegeek.quickchat.data.local.DataManager
import com.bytegeek.quickchat.data.models.UserDetail
import com.bytegeek.quickchat.data.network.NetworkResponse
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val currentUserDetail = MutableLiveData<NetworkResponse<UserDetail?>>()

    fun checkSession(): MutableLiveData<NetworkResponse<UserDetail?>> {

        val sessionLiveData = MutableLiveData<NetworkResponse<UserDetail?>>()
        sessionLiveData.postValue(NetworkResponse.loading())

        return sessionLiveData;
    }

    fun getUserDetail(context: Context): MutableLiveData<NetworkResponse<UserDetail?>> {
        currentUserDetail.postValue(NetworkResponse.loading())
        viewModelScope.launch {
            DataManager.getInstance(context).getUserDetail().collect {
                currentUserDetail.postValue(NetworkResponse.success(it))
            }
        }
        return currentUserDetail;
    }
}
