package com.cursosant.android.apprestaurant

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import roomDatabase.RestaurantDatabase

class RestaurantApplication: Application() {
    companion object{
        lateinit var database: RestaurantDatabase
    }

    override fun onCreate() {
        super.onCreate()

        val MIGRATION_1_2 = object : Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE restaurant " +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT," +
                        "direction TEXT ," +
                        "phone TEXT," +
                        "website TEXT," +
                        "urlImg TEXT, " +
                        "description TEXT ," +
                        "favorite INTEGER)")
            }
        }

        database = Room.databaseBuilder(this,
            RestaurantDatabase::class.java,
            "database-restaurant")
            .addMigrations(MIGRATION_1_2)
            .build()
    }
}