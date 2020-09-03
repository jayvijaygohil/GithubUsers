package com.jayvijaygohil.data.model

import com.jayvijaygohil.data.TimeZoneRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class RepositoryModelTest {
    @get:Rule
    val timeZoneRule = TimeZoneRule("UTC")

    @Test
    fun `verify toDomainModel converts RepositoryModel to RepositoryEntity`() {
        val subject = RepositoryModel(
            id = 1,
            name = "name",
            description = "description",
            updatedAt = "2016-02-10T08:01:21Z",
            stargazersCount = 1,
            forksCount = 0
        )

        val result = subject.toDomainModel()

        assertEquals("1", result.id)
        assertEquals("name", result.name)
        assertEquals("description", result.description)
        assertEquals("Feb 10, 2016 8:01:21 AM", result.lastUpdatedAt)
        assertEquals(1, result.starredCount)
        assertEquals(0, result.forkedCount)
    }

    @Test
    fun `verify when toDomainModel cannot parse updatedAt`() {
        val subject = RepositoryModel(
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