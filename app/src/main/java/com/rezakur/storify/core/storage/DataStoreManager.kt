package com.rezakur.storify.core.storage

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context): PrefsManager {
    override suspend fun setString(key: String, value: String) {
        context.dataStore.edit { it[stringPreferencesKey(key)] = value }
    }

    override fun getString(key: String): Flow<String?> {
        return context.dataStore.data.map { it[stringPreferencesKey(key)] }
    }

    override suspend fun setInt(key: String, value: Int) {
        context.dataStore.edit { it[intPreferencesKey(key)] = value }
    }

    override fun getInt(key: String): Flow<Int?> {
        return context.dataStore.data.map { it[intPreferencesKey(key)] }
    }

    override suspend fun setBoolean(key: String, value: Boolean) {
        context.dataStore.edit { it[booleanPreferencesKey(key)] = value }
    }

    override fun getBoolean(key: String): Flow<Boolean?> {
        return context.dataStore.data.map { it[booleanPreferencesKey(key)] }
    }

    override suspend fun setDouble(key: String, value: Double) {
        context.dataStore.edit { it[doublePreferencesKey(key)] = value }
    }

    override fun getDouble(key: String): Flow<Double?> {
        return context.dataStore.data.map { it[doublePreferencesKey(key)] }
    }

    override suspend fun setFloat(key: String, value: Float) {
        context.dataStore.edit { it[floatPreferencesKey(key)] = value }
    }

    override fun getFloat(key: String): Flow<Float?> {
        return context.dataStore.data.map { it[floatPreferencesKey(key)] }
    }

    override suspend fun setLong(key: String, value: Long) {
        context.dataStore.edit { it[longPreferencesKey(key)] = value }
    }

    override fun getLong(key: String): Flow<Long?> {
        return context.dataStore.data.map { it[longPreferencesKey(key)] }
    }

    override suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}