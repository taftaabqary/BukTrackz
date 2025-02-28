package com.unsoed.buktrackz.core.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
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

    companion object {
        private var DISPLAY_USER = booleanPreferencesKey("display_user")
    }
}