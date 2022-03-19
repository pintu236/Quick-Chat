package com.bytegeek.quickchat.ui.screens.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amazonaws.AmazonServiceException
import com.amplifyframework.core.Amplify
import com.bytegeek.quickchat.data.models.UserDetail
import com.bytegeek.quickchat.data.network.NetworkResponse

class LoginViewModel : ViewModel() {

    val loginLiveData = MutableLiveData<NetworkResponse<UserDetail?>>()
    val signUpLiveData = MutableLiveData<NetworkResponse<UserDetail?>>()

    fun confirmSignup(email: String) {
        signUpLiveData.postValue(NetworkResponse.loading())
        Amplify.Auth.resendSignUpCode(email, {
            signUpLiveData.postValue(
                NetworkResponse.success(
                    UserDetail(
                        it.user?.userId,
                        it.user?.username
                    ), "OTP Sent Successfully"
                )
            )
        }, {
            signUpLiveData.postValue(NetworkResponse.error(it.localizedMessage))
        })
    }

    fun login(email: String) {
        loginLiveData.postValue(NetworkResponse.loading())

        Amplify.Auth.signIn(email, "Pi1473690@", {
            Log.d("Auth Result", it.toString())
            if (it.isSignInComplete) {
                loginLiveData.postValue(
                    NetworkResponse.success(
                        UserDetail(
                            Amplify.Auth.currentUser.userId,
                            Amplify.Auth.currentUser.username
                        )
                    )
                )
            } else {
                loginLiveData.postValue(NetworkResponse.error("Failed", "400"))

            }

        }, {
            loginLiveData.postValue(
                NetworkResponse.error(
                    it.localizedMessage,
                    if (it.cause is AmazonServiceException) {
                        (it.cause as AmazonServiceException).errorCode
                    } else {
                        it.cause?.message
                    }

                )
            );
        })
    }
}

