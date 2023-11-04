package com.example.challenge2_binar.database.menuDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [Menu::class], version = 1, exportSchema = false)

@TypeConverters(TypeConverter::class)
abstract class MenuDatabase : RoomDatabase(){
    abstract val menuDao : MenuDao

    companion object {

        @Volatile
        private var INSTANCE: MenuDatabase? = null

        fun getInstance(context: Context): MenuDatabase {
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MenuDatabase::class.java,
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
