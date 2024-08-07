package com.robbyyehezkiel.androidfundamental2.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.robbyyehezkiel.androidfundamental2.data.model.FavoriteUser
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavoriteUser(user: FavoriteUser)

    @Delete
    suspend fun removeFavoriteUser(user: FavoriteUser)

    @Query("SELECT * FROM favorite_users")
    fun getFavoriteUsers(): Flow<List<FavoriteUser>>
}
