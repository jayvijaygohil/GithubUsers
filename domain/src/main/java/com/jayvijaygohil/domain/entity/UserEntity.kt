package com.jayvijaygohil.domain.entity

data class UserEntity(
    val id: String,
    val displayName: String?,
    val userName: String,
    val avatarUrl: String,
    val hasRepositories: Boolean
)