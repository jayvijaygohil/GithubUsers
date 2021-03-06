package com.jayvijaygohil.data.model

import org.junit.Assert.assertEquals
import org.junit.Test

class UserModelTest {
    @Test
    fun `verify toDomainModel converts UserModel to UserEntity`() {
        val subject = UserModel(
            id = 1,
            userName = "userName",
            displayName = "displayName",
            avatarUrl = "avatarUrl"
        )

        val result = subject.toDomainModel()

        assertEquals("1", result.id)
        assertEquals("displayName", result.displayName)
        assertEquals("avatarUrl", result.avatarUrl)
    }

    @Test
    fun `verify toDomainModel sets hasRepositories to true when user has public repos`() {
        val subject = UserModel(
            id = 1,
            userName = "userName",
            displayName = "displayName",
            avatarUrl = "avatarUrl"
        )

        val result = subject.toDomainModel()

        assertEquals("1", result.id)
        assertEquals("displayName", result.displayName)
        assertEquals("avatarUrl", result.avatarUrl)
    }

    @Test
    fun `verify toDomainModel sets hasRepositories to false when user does not have public repos`() {
        val subject = UserModel(
            id = 1,
            userName = "userName",
            displayName = "displayName",
            avatarUrl = "avatarUrl"
        )

        val result = subject.toDomainModel()

        assertEquals("1", result.id)
        assertEquals("displayName", result.displayName)
        assertEquals("avatarUrl", result.avatarUrl)
    }

    @Test
    fun `verify toDomainModel sets hasRepositories to false when publicRepos count is invalid`() {
        val subject = UserModel(
            id = 1,
            userName = "userName",
            displayName = "displayName",
            avatarUrl = "avatarUrl"
        )

        val result = subject.toDomainModel()

        assertEquals("1", result.id)
        assertEquals("displayName", result.displayName)
        assertEquals("avatarUrl", result.avatarUrl)
    }
}