package com.jayvijaygohil.data.model

import org.junit.Assert.*
import org.junit.Test

class RepositoryModelTest {
    @Test
    fun `verify toDomainModel converts RepositoryModel to RepositoryEntity`() {
        val subject =  RepositoryModel(
            id = 1,
            name = "name",
            description = "description",
            updatedAt = "updatedAt",
            stargazersCount = 1,
            forksCount = 0
        )

        val result = subject.toDomainModel()

        assertEquals("1", result.id)
        assertEquals("name", result.name)
        assertEquals("description", result.description)
        assertEquals("updatedAt", result.lastUpdatedAt)
        assertEquals(1, result.starredCount)
        assertEquals(0, result.forkedCount)
    }
}