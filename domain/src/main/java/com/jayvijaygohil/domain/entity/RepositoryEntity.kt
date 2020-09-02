package com.jayvijaygohil.domain.entity

data class RepositoryEntity(
    val id: String,
    val name: String,
    val description: String?,
    val lastUpdatedAt: String,
    val starredCount: Int,
    val forkedCount: Int
)