package com.jayvijaygohil.data.repository

import com.jayvijaygohil.data.model.RepositoryModel
import com.jayvijaygohil.data.model.UserModel
import com.jayvijaygohil.data.network.GithubService
import com.jayvijaygohil.domain.entity.RepositoryEntity
import com.jayvijaygohil.domain.entity.UserEntity
import com.jayvijaygohil.domain.repository.UserRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {
    private lateinit var subject: UserRepository
    private lateinit var service: GithubService

    @Before
    fun setUp() {
        service = mock()
        subject = UserRepositoryImpl(service)
    }

    @Test
    fun `verify searchUser calls GithubService's searchUser`() = runBlockingTest {
        val user = "John"

        subject.searchUser(user)

        verify(service).searchUser(user)
    }

    @Test
    fun `verify searchUser returns a UserEntity when there is a response from the service`() =
        runBlockingTest {
            val user = "John"
            val data = UserModel(
                id = 1,
                userName = "userName",
                displayName = "displayName",
                avatarUrl = "avatarUrl"
            )

            whenever(service.searchUser(user)).thenReturn(data)

            val result = subject.searchUser(user)

            assertTrue(result is UserEntity)
        }

    @Test
    fun `verify searchUser returns null when service responds with null`() = runBlockingTest {
        val user = "John"
        whenever(service.searchUser(user)).thenReturn(null)

        val result = subject.searchUser(user)

        assertNull(result)
    }

    @Test
    fun `verify fetchUserRepositories calls GithubService's fetchUserRepositories`() =
        runBlockingTest {
            val user = "John"

            subject.fetchUserRepositories(user)

            verify(service).fetchRepositories(user)
        }

    @Test
    fun `verify fetchUserRepositories returns a RepositoryEntity when there is a response from the service`() =
        runBlockingTest {
            val user = "John"
            val data = listOf(
                RepositoryModel(
                    id = 1,
                    name = "name",
                    description = "description",
                    updatedAt = "updatedAt",
                    stargazersCount = 0,
                    forksCount = 0
                )
            )

            whenever(service.fetchRepositories(user)).thenReturn(data)

            val result = subject.fetchUserRepositories(user)

            assertEquals(1, result!!.size)
            assertTrue(result[0] is RepositoryEntity)
        }

    @Test
    fun `verify fetchUserRepositories returns null when service responds with an empty list`() =
        runBlockingTest {
            val user = "John"
            whenever(service.fetchRepositories(user)).thenReturn(emptyList<RepositoryModel>())

            val result = subject.fetchUserRepositories(user)

            assertNull(result)
        }

    @Test
    fun `verify fetchUserRepositories returns null when service responds with null`() =
        runBlockingTest {
            val user = "John"
            whenever(service.fetchRepositories(user)).thenReturn(null)

            val result = subject.fetchUserRepositories(user)

            assertNull(result)
        }
}