package com.jayvijaygohil.data.repository

import com.jayvijaygohil.data.model.toDomainModel
import com.jayvijaygohil.data.network.GithubService
import com.jayvijaygohil.domain.entity.RepositoryEntity
import com.jayvijaygohil.domain.entity.UserEntity
import com.jayvijaygohil.domain.repository.UserRepository

internal class UserRepositoryImpl(
    private val githubService: GithubService
) : UserRepository {
    override suspend fun searchUser(userName: String): UserEntity? =
        githubService.searchUser(userName)?.toDomainModel()

    override suspend fun fetchUserRepositories(userName: String): List<RepositoryEntity>? {
        val result = githubService.fetchRepositories(userName)?.map { it.toDomainModel() }
        return if (result.isNullOrEmpty()) null else result
    }
}