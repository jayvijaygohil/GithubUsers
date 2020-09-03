package com.jayvijaygohil.domain.usecase

import com.jayvijaygohil.domain.entity.UserEntity
import com.jayvijaygohil.domain.repository.UserRepository
import com.jayvijaygohil.domain.usecase.SearchUserUseCase.Result.Error
import com.jayvijaygohil.domain.usecase.SearchUserUseCase.Result.NoSuchUserFound
import com.jayvijaygohil.domain.usecase.SearchUserUseCase.Result.Success
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SearchUserUseCaseImplTest {

    private lateinit var subject: SearchUserUseCase
    private lateinit var repository: UserRepository

    @Before
    fun setUp() {
        repository = mock()
        subject = SearchUserUseCaseImpl(repository)
    }

    @Test
    fun `verify execute fetches the data when request is successful`() = runBlockingTest {
        val user = "John"
        val data = UserEntity(
            id = "id",
            displayName = "displayName",
            avatarUrl = "avatarUrl"
        )
        whenever(repository.searchUser(user)).thenReturn(data)

        val result = subject.execute(user)

        verify(repository).searchUser(user)
        assertEquals(Success(data), result)
    }

    @Test
    fun `verify execute handles null response`() = runBlockingTest {
        val user = "John"
        whenever(repository.searchUser(user)).thenReturn(null)

        val result = subject.execute(user)

        verify(repository).searchUser(user)
        assertEquals(NoSuchUserFound, result)
    }

    @Test
    fun `verify execute handles an exception`() = runBlockingTest {
        val user = "John"
        val exception = IllegalArgumentException("TestException")
        whenever(repository.searchUser(user)).thenThrow(exception)

        val result = subject.execute(user)

        verify(repository).searchUser(user)
        assertEquals(Error(exception), result)
    }
}