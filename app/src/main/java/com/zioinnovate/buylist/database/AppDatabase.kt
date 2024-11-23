package com.zioinnovate.buylist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Lista::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun listaDao(): ListaDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "buylist.db").build()
            }
            return INSTANCE!!
        }
    }
}