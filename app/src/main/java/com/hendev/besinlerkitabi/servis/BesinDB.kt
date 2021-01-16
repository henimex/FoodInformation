package com.hendev.besinlerkitabi.servis

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hendev.besinlerkitabi.model.Besin


@Database(entities = arrayOf(Besin::class), version = 1, exportSchema = true)
abstract class BesinDB : RoomDatabase() {

    abstract fun besinDao(): BesinDAO

    companion object{

        @Volatile private var instance : BesinDB?= null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: makeDB(context).also {
                instance = it
            }
        }

        private fun makeDB(context: Context) = Room.databaseBuilder(
            context.applicationContext, BesinDB::class.java, "BesinDataBase"
        ).build()
    }
}