package com.uhi5d.statibud.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.uhi5d.statibud.di.PREF_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(context: Context) {

    private val TOKEN = stringPreferencesKey("token")
    private val CODE_CHALLENGE = stringPreferencesKey("code_challenge")
    private val CODE_VERIFIER = stringPreferencesKey("code_verifier")

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREF_NAME)

    private val mDataStore = context.dataStore

    suspend fun saveToken(token: String){
        mDataStore.edit { prefs ->
            prefs[TOKEN] = token
        }
    }

    val getToken : Flow<String> = mDataStore.data.map { prefs ->
        prefs[TOKEN] ?: ""
    }

    suspend fun saveCodeChallenge(cc: String){
        mDataStore.edit { prefs->
            prefs[CODE_CHALLENGE] = cc
        }
    }

    val getCodeChallenge: Flow<String> = mDataStore.data.map { prefs ->
        prefs[CODE_CHALLENGE] ?: ""
    }

    suspend fun saveCodeVerifier(cv: String){
        mDataStore.edit { prefs->
            prefs[CODE_VERIFIER] = cv
        }
    }

    val getCodeVerifier: Flow<String> = mDataStore.data.map { prefs ->
        prefs[CODE_VERIFIER] ?: ""
    }
}