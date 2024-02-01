package com.robbyyehezkiel.androidfundamental2.data.model

data class User(
    val login: String,
    val avatar_url: String,
    val name: String,
    val followers: Int,
    val following: Int
)