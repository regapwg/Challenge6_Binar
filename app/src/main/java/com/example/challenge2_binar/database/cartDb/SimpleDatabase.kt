package com.example.challenge2_binar.database.cartDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SimpleChart::class], version = 4, exportSchema = false)
abstract class SimpleDatabase : RoomDatabase(){
    abstract val simpleChartDao : SimpleChartDao

    companion object {

        @Volatile
        private var INSTANCE: SimpleDatabase? = null

        fun getInstance(context: Context): SimpleDatabase {
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SimpleDatabase::class.java,
                        "simple_database"
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}