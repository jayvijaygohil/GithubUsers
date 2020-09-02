package com.jayvijaygohil.domain.usecase

import com.jayvijaygohil.domain.entity.RepositoryEntity
import com.jayvijaygohil.domain.repository.UserRepository
import com.jayvijaygohil.domain.usecase.FetchRepositoriesUseCase.Result.Error
import com.jayvijaygohil.domain.usecase.FetchRepositoriesUseCase.Result.NoRepositories
import com.jayvijaygohil.domain.usecase.FetchRepositoriesUseCase.Result.Success
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FetchRepositoriesUseCaseImplTest {

    private lateinit var subject: FetchRepositoriesUseCase
    private lateinit var repository: UserRepository

    @Before
    fun setUp() {
        repository = mock()
        subject = FetchRepositoriesUseCaseImpl(repository)
    }

    @Test
    fun `verify execute fetches the data when request is successful`() = runBlockingTest {
        val user = "John"
        val data = listOf(
            RepositoryEntity(
                id = "id0",
                name = "name0",
                description = "description",
                lastUpdatedAt = "DateTime",
                starredCount = 2,
                forkedCount = 0
            )
        )
        whenever(repository.fetchUserRepositories(user)).thenReturn(data)

        val result = subject.execute(user)

        verify(repository).fetchUserRepositories(user)
        assertEquals(Success(data), result)
    }

    @Test
    fun `verify execute handles empty list response`() = runBlockingTest {
        val user = "John"
        val data = emptyList<RepositoryEntity>()
        whenever(repository.fetchUserRepositories(user)).thenReturn(data)

        val result = subject.execute(user)

        verify(repository).fetchUserRepositories(user)
        assertEquals(NoRepositories, result)
    }

    @Test
    fun `verify execute handles null response`() = runBlockingTest {
        val user = "John"
        whenever(repository.fetchUserRepositories(user)).thenReturn(null)

        val result = subject.execute(user)

        verify(repository).fetchUserRepositories(user)
        assertEquals(NoRepositories, result)
    }

    @Test
    fun `verify execute handles an exception`() = runBlockingTest {
        val user = "John"
        val exception = IllegalArgumentException("TestException")
        whenever(repository.fetchUserRepositories(user)).thenThrow(exception)

        val result = subject.execute(user)

        verify(repository).fetchUserRepositories(user)
        assertEquals(Error(exception), result)
    }
}