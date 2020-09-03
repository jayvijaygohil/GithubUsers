package com.jayvijaygohil.githubusers.feature.user_info

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jayvijaygohil.domain.entity.RepositoryEntity
import com.jayvijaygohil.domain.entity.UserEntity
import com.jayvijaygohil.domain.usecase.FetchRepositoriesUseCase
import com.jayvijaygohil.domain.usecase.SearchUserUseCase
import com.jayvijaygohil.githubusers.CoroutineTestRule
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.IllegalArgumentException

class UserInfoViewModelTest {
    @get:Rule
    val taskRule = InstantTaskExecutorRule()
    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private lateinit var searchUserUseCase: SearchUserUseCase
    private lateinit var fetchRepositoriesUseCase: FetchRepositoriesUseCase

    private lateinit var subject: UserInfoViewModel

    @Before
    fun setup() {
        searchUserUseCase = mock()
        fetchRepositoriesUseCase = mock()

        subject = UserInfoViewModel(searchUserUseCase, fetchRepositoriesUseCase)
    }

    @Test
    fun `verify valid searchUser flow for Result_Success`() = runBlockingTest {
        val searchKeyword = "johndoe"
        val userEntity = UserEntity(
            id = "id",
            displayName = "displayName",
            avatarUrl = "avatarUrl"
        )
        val repoEntityList = listOf(
            RepositoryEntity(
                "id",
                name = "name",
                description = "description",
                lastUpdatedAt = "lastUpdatedAt",
                starredCount = 20,
                forkedCount = 20
            )
        )

        // Case 1 - searchUserUseCase and fetchRepositoriesUseCase return Success
        whenever(searchUserUseCase.execute(searchKeyword))
            .thenReturn(SearchUserUseCase.Result.Success(userEntity))
        whenever(fetchRepositoriesUseCase.execute(searchKeyword))
            .thenReturn(FetchRepositoriesUseCase.Result.Success(repoEntityList))

        subject.searchUser(searchKeyword)

        verify(searchUserUseCase).execute(searchKeyword)
        verify(fetchRepositoriesUseCase).execute(searchKeyword)

        assertEquals(false, subject.stateLiveData.value!!.isError)
        assertEquals(userEntity, subject.stateLiveData.value!!.userInfo)
        assertEquals(repoEntityList, subject.stateLiveData.value!!.repositories)

        // Case 2 - searchUserUseCase returns Success and fetchRepositoriesUseCase returns Error
        whenever(searchUserUseCase.execute(searchKeyword))
            .thenReturn(SearchUserUseCase.Result.Success(userEntity))
        whenever(fetchRepositoriesUseCase.execute(searchKeyword))
            .thenReturn(FetchRepositoriesUseCase.Result.Error(IllegalArgumentException()))

        subject.searchUser(searchKeyword)

        verify(searchUserUseCase, times(2)).execute(searchKeyword)
        verify(fetchRepositoriesUseCase, times(2)).execute(searchKeyword)

        assertEquals(false, subject.stateLiveData.value!!.isError)
        assertEquals(userEntity, subject.stateLiveData.value!!.userInfo)
        assertTrue(subject.stateLiveData.value!!.repositories.isEmpty())

        verifyZeroInteractions(searchUserUseCase)
        verifyZeroInteractions(fetchRepositoriesUseCase)
    }

    @Test
    fun `verify valid searchUser flow for Result_Error`() = runBlockingTest {
        val searchKeyword = "johndoe"
        val repoEntityList = listOf(
            RepositoryEntity(
                "id",
                name = "name",
                description = "description",
                lastUpdatedAt = "lastUpdatedAt",
                starredCount = 20,
                forkedCount = 20
            )
        )

        // Case 1 - searchUserUseCase and fetchRepositoriesUseCase return Error
        whenever(searchUserUseCase.execute(searchKeyword))
            .thenReturn(SearchUserUseCase.Result.Error(IllegalArgumentException()))
        whenever(fetchRepositoriesUseCase.execute(searchKeyword))
            .thenReturn(FetchRepositoriesUseCase.Result.Error(IllegalArgumentException()))

        subject.searchUser(searchKeyword)

        verify(searchUserUseCase).execute(searchKeyword)
        verify(fetchRepositoriesUseCase).execute(searchKeyword)

        assertEquals(true, subject.stateLiveData.value!!.isError)
        assertNull(subject.stateLiveData.value!!.userInfo)
        assertTrue(subject.stateLiveData.value!!.repositories.isEmpty())

        // Case 2 - searchUserUseCase return Error and fetchRepositoriesUseCase return Success
        whenever(fetchRepositoriesUseCase.execute(searchKeyword))
            .thenReturn(FetchRepositoriesUseCase.Result.Success(repoEntityList))

        subject.searchUser(searchKeyword)

        verify(searchUserUseCase, times(2)).execute(searchKeyword)
        verify(fetchRepositoriesUseCase, times(2)).execute(searchKeyword)

        assertEquals(true, subject.stateLiveData.value!!.isError)
        assertNull(subject.stateLiveData.value!!.userInfo)
        assertTrue(subject.stateLiveData.value!!.repositories.isEmpty())

        verifyZeroInteractions(searchUserUseCase)
        verifyZeroInteractions(fetchRepositoriesUseCase)
    }

    @Test
    fun `verify a request is not sent if searchKeyword is null or blank`() {
        subject.searchUser(null)
        subject.searchUser("")
        subject.searchUser("  ")

        verifyZeroInteractions(searchUserUseCase)
        verifyZeroInteractions(fetchRepositoriesUseCase)
    }
}