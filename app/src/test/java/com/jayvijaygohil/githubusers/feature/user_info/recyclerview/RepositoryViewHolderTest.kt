package com.jayvijaygohil.githubusers.feature.user_info.recyclerview

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.test.core.app.ApplicationProvider
import com.jayvijaygohil.domain.entity.RepositoryEntity
import com.jayvijaygohil.githubusers.R
import com.jayvijaygohil.githubusers.TestApplication
import com.jayvijaygohil.githubusers.common.ItemClickListener
import com.jayvijaygohil.githubusers.databinding.ItemRepositoryBinding
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [29], application = TestApplication::class)
class RepositoryViewHolderTest {
    private lateinit var context: Context
    private lateinit var inflatedView: View
    private lateinit var clickListener: ItemClickListener
    private lateinit var subject: RepositoryViewHolder

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext<Application>()
        inflatedView = LayoutInflater.from(context).inflate(R.layout.item_repository, null, true)
        clickListener = mock()

        subject = RepositoryViewHolder(ItemRepositoryBinding.bind(inflatedView), clickListener)
    }

    @Test
    fun `verify repositoryTitle is shown if RepositoryEntity has valid name`() {
        val data = RepositoryEntity(
            "id",
            name = "name",
            description = "description",
            lastUpdatedAt = "lastUpdatedAt",
            starredCount = 20,
            forkedCount = 20
        )

        subject.bind(data)

        assertTrue(subject.binding.repositoryTitle.isVisible)
        assertEquals("name", subject.binding.repositoryTitle.text)
    }

    @Test
    fun `verify repositoryTitle is hidden if RepositoryEntity does not have a valid name`() {
        val data = RepositoryEntity(
            "id",
            name = "",
            description = "description",
            lastUpdatedAt = "lastUpdatedAt",
            starredCount = 20,
            forkedCount = 20
        )

        subject.bind(data)

        assertTrue(subject.binding.repositoryTitle.isGone)
    }

    @Test
    fun `verify repositoryDescription is shown if RepositoryEntity has valid description`() {
        val data = RepositoryEntity(
            "id",
            name = "name",
            description = "description",
            lastUpdatedAt = "lastUpdatedAt",
            starredCount = 20,
            forkedCount = 20
        )

        subject.bind(data)

        assertTrue(subject.binding.repositoryDescription.isVisible)
        assertEquals("description", subject.binding.repositoryDescription.text)
    }

    @Test
    fun `verify repositoryDescription is hidden if RepositoryEntity does not have a valid description`() {
        val data = RepositoryEntity(
            "id",
            name = "name",
            description = "",
            lastUpdatedAt = "lastUpdatedAt",
            starredCount = 20,
            forkedCount = 20
        )

        subject.bind(data)

        assertTrue(subject.binding.repositoryDescription.isGone)
    }
}