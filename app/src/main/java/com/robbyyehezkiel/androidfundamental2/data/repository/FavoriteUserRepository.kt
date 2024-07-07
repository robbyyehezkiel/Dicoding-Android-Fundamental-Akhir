package com.robbyyehezkiel.androidfundamental2.data.repository

import com.robbyyehezkiel.androidfundamental2.data.database.FavoriteUserDao
import com.robbyyehezkiel.androidfundamental2.data.model.FavoriteUser
import kotlinx.coroutines.flow.Flow

class FavoriteUserRepository(private val favoriteUserDao: FavoriteUserDao) {
    suspend fun addFavoriteUser(user: FavoriteUser) {
        favoriteUserDao.addFavoriteUser(user)
    }

    suspend fun removeFavoriteUser(user: FavoriteUser) {
        favoriteUserDao.removeFavoriteUser(user)
    }

    fun getFavoriteUsers(): Flow<List<FavoriteUser>> {
        return favoriteUserDao.getFavoriteUsers()
    }
}
