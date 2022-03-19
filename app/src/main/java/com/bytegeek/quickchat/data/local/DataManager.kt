package com.bytegeek.quickchat.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bytegeek.quickchat.data.models.UserDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

public class DataManager private constructor(val context: Context) {


    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "quick_chat_prefs")
    val IS_EMAIL_VERIFIED = booleanPreferencesKey("is_email_verified")
    val EMAIL = stringPreferencesKey("email")
    val USER_ID = stringPreferencesKey("user_id")

    companion object : SingletonHolder<DataManager, Context>(::DataManager)


    suspend fun saveUserDetail(userDetail: UserDetail?) {
        context.dataStore.edit {
            it[EMAIL] = userDetail?.email.toString()
            it[USER_ID] = userDetail?.userId.toString()
        }
    }

    suspend fun getUserDetail(): Flow<UserDetail> {
        return context.dataStore.data.map {
            UserDetail(it[EMAIL], it[USER_ID])
        }
    }

    suspend fun saveIsEmailVerified(state: Boolean) {
        context.dataStore.edit {
            it[IS_EMAIL_VERIFIED] = state
        }
    }

    suspend fun isEmailVerified(): Flow<Boolean> {
        return context.dataStore.data.map {
            it[IS_EMAIL_VERIFIED] ?: false
        }
    }

    open class SingletonHolder<out T : Any, in A>(creator: (A) -> T) {
        private var creator: ((A) -> T)? = creator

        @Volatile
        private var instance: T? = null

        fun getInstance(arg: A): T {
            val i = instance
            if (i != null) {
                return i
            }

            return synchronized(this) {
                val i2 = instance
                if (i2 != null) {
                    i2
                } else {
                    val created = creator!!(arg)
                    instance = created
                    creator = null
                    created
                }
            }
        }
    }
}