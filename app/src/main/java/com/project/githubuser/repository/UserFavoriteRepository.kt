package com.project.githubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.project.githubuser.database.UserFavorite
import com.project.githubuser.database.UserFavoriteDao
import com.project.githubuser.database.UserFavoriteRoomDatabase
import com.project.githubuser.model.user.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

class UserFavoriteRepository(application: Application) {

    private val userFavoriteDao: UserFavoriteDao
    private val dispatcher = Executors.newCachedThreadPool().asCoroutineDispatcher()
    private val scope = CoroutineScope(dispatcher)

    init {
        val db = UserFavoriteRoomDatabase.getDatabase(application)
        userFavoriteDao = db.userFavoriteDao()
    }

    fun getAllUserFavorite(): LiveData<List<UserFavorite>> = userFavoriteDao.getAllUserFavorite()

    fun insert(userFavorite: UserFavorite) {
        scope.launch { userFavoriteDao.insert(userFavorite) }
    }

    suspend fun delete(user: User): Int {
        return withContext(scope.coroutineContext) { userFavoriteDao.delete(user.id) }
    }

    suspend fun checkedUser(user: User): Int {
        return withContext(scope.coroutineContext) {
            userFavoriteDao.checkUser(
                user.id
            )
        }
    }
}