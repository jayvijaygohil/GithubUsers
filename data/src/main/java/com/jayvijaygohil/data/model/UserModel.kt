package com.jayvijaygohil.data.model

import com.google.gson.annotations.SerializedName
import com.jayvijaygohil.domain.entity.UserEntity

data class UserModel(
    val id: Int,
    @field:SerializedName("login") val userName: String,
    @field:SerializedName("name") val displayName: String?,
    @field:SerializedName("avatar_url") val avatarUrl: String
)

fun UserModel.toDomainModel(): UserEntity {
    val name = if (displayName.isNullOrBlank()) userName else displayName

    return UserEntity(
        id = id.toString(),
        displayName = name,
        avatarUrl = avatarUrl
    )
}