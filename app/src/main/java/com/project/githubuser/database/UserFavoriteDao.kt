package com.project.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserFavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(userFavorite: UserFavorite)

    @Query("SELECT * FROM UserFavorite ORDER BY id ASC")
    fun getAllUserFavorite(): LiveData<List<UserFavorite>>

    @Query("DELETE FROM UserFavorite WHERE UserFavorite.id = :id")
    fun delete(id: Int): Int

    @Query("SELECT COUNT(*) FROM UserFavorite WHERE UserFavorite.id = :id")
    fun checkUser(id: Int): Int

}