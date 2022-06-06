package com.project.githubuser.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserFavorite::class], version = 1)
abstract class UserFavoriteRoomDatabase : RoomDatabase() {

    abstract fun userFavoriteDao(): UserFavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: UserFavoriteRoomDatabase? = null

        fun getDatabase(context: Context): UserFavoriteRoomDatabase {
            if (INSTANCE == null) {
                synchronized(UserFavoriteRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserFavoriteRoomDatabase::class.java,
                        "user_favorite_database"
                    )
                        .build()
                }
            }
            return INSTANCE as UserFavoriteRoomDatabase
        }
    }

}