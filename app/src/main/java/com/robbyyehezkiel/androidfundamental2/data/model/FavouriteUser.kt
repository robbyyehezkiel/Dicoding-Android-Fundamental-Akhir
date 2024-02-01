package com.robbyyehezkiel.androidfundamental2.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_users")
data class FavoriteUser(
    @PrimaryKey val login: String,
    val avatarUrl: String,
    val name: String,
    val followers: Int,
    val following: Int
)