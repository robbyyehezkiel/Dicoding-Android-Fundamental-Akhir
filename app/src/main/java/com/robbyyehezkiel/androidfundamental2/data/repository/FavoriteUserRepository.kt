package com.robbyyehezkiel.androidfundamental2.data.repository

import com.robbyyehezkiel.androidfundamental2.data.database.FavoriteUserDao
import com.robbyyehezkiel.androidfundamental2.data.model.FavoriteUser

class FavoriteUserRepository(private val favoriteUserDao: FavoriteUserDao) {
    suspend fun addFavoriteUser(user: FavoriteUser) {
        favoriteUserDao.addFavoriteUser(user)
    }

    suspend fun removeFavoriteUser(user: FavoriteUser) {
        favoriteUserDao.removeFavoriteUser(user)
    }

    suspend fun getFavoriteUsers(): List<FavoriteUser> {
        return favoriteUserDao.getFavoriteUsers()
    }
}
