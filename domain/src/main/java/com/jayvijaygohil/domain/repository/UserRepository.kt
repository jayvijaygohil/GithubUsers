package com.jayvijaygohil.domain.repository

import com.jayvijaygohil.domain.entity.RepositoryEntity
import com.jayvijaygohil.domain.entity.UserEntity

interface UserRepository {
    suspend fun searchUser(userName: String): UserEntity?
    suspend fun fetchUserRepositories(userName: String): List<RepositoryEntity>?
}