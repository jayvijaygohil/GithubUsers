package com.jayvijaygohil.domain.usecase

import com.jayvijaygohil.domain.entity.UserEntity
import com.jayvijaygohil.domain.repository.UserRepository
import com.jayvijaygohil.domain.usecase.SearchUserUseCase.Result

interface SearchUserUseCase {
    sealed class Result {
        object NoSuchUserFound : Result()
        data class Success(val data: UserEntity) : Result()
        data class Error(val e: Throwable) : Result()
    }

    suspend fun execute(userName: String): Result
}

internal class SearchUserUseCaseImpl(private val userRepository: UserRepository) :
    SearchUserUseCase {
    override suspend fun execute(userName: String): Result =
        runCatching {
            userRepository.searchUser(userName)?.let { Result.Success(it) }
                ?: Result.NoSuchUserFound
        }.getOrElse { Result.Error(it) }
}