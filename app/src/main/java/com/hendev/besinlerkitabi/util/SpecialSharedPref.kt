package com.hendev.besinlerkitabi.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SpecialSharedPref {

    companion object {

        private val ZAMAN = "zaman"
        private var sharedPreferences: SharedPreferences? = null

        @Volatile
        private var instance: SpecialSharedPref? = null
        private val lock = Any()

        operator fun invoke(context: Context): SpecialSharedPref = instance ?: synchronized(lock) {
            instance ?: createSpecialSP(context).also {
                instance = it
                //dsds da dsa
            }
        }

        private fun createSpecialSP(context: Context): SpecialSharedPref {
            sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return SpecialSharedPref()
        }
    }

    fun zamaniKaydet(zaman: Long) {
        sharedPreferences?.edit(commit = true){
            putLong(ZAMAN,zaman)
        }
    }

    fun zamaniAl() = sharedPreferences?.getLong(ZAMAN,0)
}