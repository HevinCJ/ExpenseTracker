package com.example.expensetracker.DataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.map
import kotlin.coroutines.coroutineContext


class DataStoreRepository(context: Context) {


    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Tracker_Datastore")

    companion object{
        val TOTAL_INCOME = doublePreferencesKey("total_income")

    }

    fun read(key:Preferences.Key<*>,context: Context){
        context.dataStore.data.map {preferences->
            preferences[key]
        }
    }

    suspend fun saveIncome(income: Double, context: Context) {
        context.dataStore.edit { preferences ->
            val currentTotalIncome = preferences[TOTAL_INCOME] ?: 0.0
            preferences[TOTAL_INCOME] = currentTotalIncome + income
        }
    }





}
