package com.robbyyehezkiel.androidfundamental2.data.repository


import com.robbyyehezkiel.androidfundamental2.data.model.User
import com.robbyyehezkiel.androidfundamental2.data.model.UsersResponse

interface GitHubRepository {
    suspend fun searchUsers(query: String): UsersResponse
    suspend fun getUserDetail(username: String): User
    suspend fun getUserFollowers(username: String): List<User>
    suspend fun getUserFollowing(username: String): List<User>
}