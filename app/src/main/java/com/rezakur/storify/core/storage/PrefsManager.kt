package com.rezakur.storify.core.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.rezakur.storify.core.constant.PrefConstants
import kotlinx.coroutines.flow.Flow

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PrefConstants.PREFS_NAME)

interface PrefsManager {
    suspend fun setString(key: String, value: String)

    fun getString(key: String): Flow<String?>

    suspend fun setInt(key: String, value: Int)

    fun getInt(key: String): Flow<Int?>

    suspend fun setBoolean(key: String, value: Boolean)

    fun getBoolean(key: String): Flow<Boolean?>

    suspend fun setDouble(key: String, value: Double)

    fun getDouble(key: String): Flow<Double?>

    suspend fun setFloat(key: String, value: Float)

    fun getFloat(key: String): Flow<Float?>

    suspend fun setLong(key: String, value: Long)

    fun getLong(key: String): Flow<Long?>

    suspend fun clear()
}