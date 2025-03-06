package com.unsoed.buktrackz.core.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class Preferences(private val dataStore: DataStore<Preferences>)  {

    fun getDisplayUser(): Flow<Boolean> {
        return dataStore.data.map { data ->
            data[DISPLAY_USER] ?: false
        }
    }

    suspend fun saveDisplayUser(displayState: Boolean) {
        dataStore.edit { data ->
            data[DISPLAY_USER] = displayState
        }
    }

    fun getLanguageUser(): Flow<String> {
        return dataStore.data.map { data ->
            data[LANGUAGE_USER] ?: "en"
        }
    }

    suspend fun saveLanguageUser(language: String) {
        dataStore.edit { data ->
            data[LANGUAGE_USER] = language
        }
    }

    companion object {
        private var DISPLAY_USER = booleanPreferencesKey("display_user")
        private var LANGUAGE_USER = stringPreferencesKey("language_user")
    }
}