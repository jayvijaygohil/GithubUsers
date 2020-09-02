package com.jayvijaygohil.data.model

import com.google.gson.annotations.SerializedName
import com.jayvijaygohil.domain.entity.RepositoryEntity

data class RepositoryModel(
    val id: Int,
    val name: String,
    val description: String?,
    val updatedAt: String,
    val stargazersCount: Int,
    @SerializedName("forks_count") val forksCount: Int
)

fun RepositoryModel.toDomainModel() = RepositoryEntity(
    id = id.toString(),
    name = name,
    description = description,
    lastUpdatedAt = updatedAt,
    starredCount = stargazersCount,
    forkedCount = forksCount
)