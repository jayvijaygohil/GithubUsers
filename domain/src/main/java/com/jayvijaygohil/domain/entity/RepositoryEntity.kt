package com.jayvijaygohil.domain.entity

import java.io.Serializable

data class RepositoryEntity(
    val id: String,
    val name: String,
    val description: String?,
    val lastUpdatedAt: String,
    val starredCount: Int,
    val forkedCount: Int
) : Serializable