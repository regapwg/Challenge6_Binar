package com.example.challenge2_binar.database.categoryDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [Category::class], version = 2, exportSchema = false)

@TypeConverters(ConverterCategoryMenu::class)
abstract class CategoryDatabase : RoomDatabase(){
    abstract val categoryDao: CategoryDao

    companion object {

        @Volatile
        private var INSTANCE: CategoryDatabase? = null

        fun getInstance(context: Context): CategoryDatabase {
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CategoryDatabase::class.java,
                        "category_database"
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