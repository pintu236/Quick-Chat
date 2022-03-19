package com.bytegeek.quickchat.ui.screens.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify
import com.bytegeek.quickchat.data.local.DataManager
import com.bytegeek.quickchat.data.models.UserDetail
import com.bytegeek.quickchat.data.network.NetworkResponse
import kotlin.coroutines.coroutineContext

class SignUpViewModel : ViewModel() {

    val signUpLiveData =
        MutableLiveData<NetworkResponse<UserDetail?>>()

    fun signupUser(email: String) {
        signUpLiveData.postValue(NetworkResponse.loading())
        val authOptions = AuthSignUpOptions.builder()
            .userAttribute(AuthUserAttributeKey.email(), email)
            .build();

        Amplify.Auth.signUp(email, "Pi1473690@", authOptions, {
            signUpLiveData.postValue(
                NetworkResponse.success(
                    UserDetail(it.user?.userId, it.user?.username),
                    "You have registered successfully! Please verify your email now."
                ))
        }, {
            signUpLiveData.postValue(NetworkResponse.error(it.localizedMessage))
        });
    }
}