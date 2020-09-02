package com.jayvijaygohil.data.model

import com.google.gson.annotations.SerializedName
import com.jayvijaygohil.domain.entity.UserEntity

data class UserModel(
    val id: Int,
    @field:SerializedName("login") val userName: String,
    @field:SerializedName("name") val displayName: String,
    @field:SerializedName("avatar_url") val avatarUrl: String,
    @field:SerializedName("public_repos") val publicRepos: Int
)

fun UserModel.toDomainModel() = UserEntity(
    id = id.toString(),
    userName = userName,
    displayName = displayName,
    avatarUrl = avatarUrl,
    hasRepositories = publicRepos > 0
)