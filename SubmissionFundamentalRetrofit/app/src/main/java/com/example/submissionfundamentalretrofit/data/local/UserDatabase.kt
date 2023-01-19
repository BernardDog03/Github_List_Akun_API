package com.example.submissionfundamentalretrofit.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 2, exportSchema = true)
abstract class UserDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao

    companion object{
        @Volatile
        private var instance: UserDatabase? = null
        fun getInstance(context: Context): UserDatabase = instance?: synchronized(this){
            instance?: Room.databaseBuilder(
                context.applicationContext,
                UserDatabase::class.java, "User.db"
            ).fallbackToDestructiveMigration().build()
        }
    }
}