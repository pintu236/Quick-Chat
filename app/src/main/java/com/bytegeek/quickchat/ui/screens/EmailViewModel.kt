package com.bytegeek.quickchat.ui.screens

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.core.Amplify
import com.bytegeek.quickchat.data.local.DataManager
import com.bytegeek.quickchat.data.models.UserDetail
import com.bytegeek.quickchat.data.network.NetworkResponse
import kotlinx.coroutines.launch

class EmailViewModel : ViewModel() {

    val verifyLiveData = MutableLiveData<NetworkResponse<UserDetail?>>()
    val userDetailState = MutableLiveData<UserDetail>()

    fun verifyEmail(email: String, code: String) {
        Log.d("Values For Email:", String.format("%1s : %1s", email, code))
        verifyLiveData.postValue(NetworkResponse.loading())
        Amplify.Auth.confirmSignUp(email, code, {
            if (it.isSignUpComplete) {

                verifyLiveData.postValue(
                    NetworkResponse.success(
                        UserDetail(
                            it.user?.userId, it.user?.username
                        )
                    )
                )
            } else {
                verifyLiveData.postValue(NetworkResponse.error("Some error occurred"))
            }

        }, {
            verifyLiveData.postValue(NetworkResponse.error(it.localizedMessage))
        })
    }

    fun getUserDetail(context: Context): MutableLiveData<UserDetail> {
        viewModelScope.launch {
            DataManager.getInstance(context).getUserDetail().collect {
                userDetailState.value = it
            }
        }
        return userDetailState
    }

}
