package com.jayvijaygohil.domain.usecase

import com.jayvijaygohil.domain.entity.RepositoryEntity
import com.jayvijaygohil.domain.repository.UserRepository
import com.jayvijaygohil.domain.usecase.FetchRepositoriesUseCase.Result

interface FetchRepositoriesUseCase {
    sealed class Result {
        object NoRepositories : Result()
        data class Success(val data: List<RepositoryEntity>) : Result()
        data class Error(val e: Throwable) : Result()
    }

    suspend fun execute(userName: String): Result
}

internal class FetchRepositoriesUseCaseImpl(
    private val userRepository: UserRepository
) : FetchRepositoriesUseCase {
    override suspend fun execute(userName: String): Result = runCatching {
        userRepository.fetchUserRepositories(userName)
            ?.takeIf { it.isNotEmpty() }
            ?.let { Result.Success(it) } ?: Result.NoRepositories
    }.getOrElse { Result.Error(it) }
}