package com.jayvijaygohil.githubusers.feature.user_info

import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Looper.getMainLooper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.view.get
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import com.jayvijaygohil.domain.entity.RepositoryEntity
import com.jayvijaygohil.domain.entity.UserEntity
import com.jayvijaygohil.domain.usecase.FetchRepositoriesUseCase
import com.jayvijaygohil.domain.usecase.SearchUserUseCase
import com.jayvijaygohil.githubusers.CoroutineTestRule
import com.jayvijaygohil.githubusers.TestApplication
import com.jayvijaygohil.githubusers.common.ItemClickListener
import com.jayvijaygohil.githubusers.feature.repository_detail.RepoDetailDialog
import com.jayvijaygohil.githubusers.feature.user_info.recyclerview.RepositoryAdapter
import com.jayvijaygohil.githubusers.feature.user_info.recyclerview.SpaceItemDecoration
import com.jayvijaygohil.githubusers.getFakeErrorImageLoader
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import org.robolectric.annotation.LooperMode.Mode.PAUSED

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [29], application = TestApplication::class)
@LooperMode(PAUSED)
class UserInfoFragmentTest : AutoCloseKoinTest() {

    @get:Rule
    val taskRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private lateinit var fragmentScenario: FragmentScenario<UserInfoFragment>
    private lateinit var viewModel: UserInfoViewModel
    private lateinit var searchUserUseCase: SearchUserUseCase
    private lateinit var fetchRepositoriesUseCase: FetchRepositoriesUseCase

    @Before
    fun setup() {
        setupKoin()
        viewModel = get()
        searchUserUseCase = get()
        fetchRepositoriesUseCase = get()
    }

    @After
    fun tearDown() {
        if (::fragmentScenario.isInitialized) fragmentScenario.moveToState(Lifecycle.State.DESTROYED)
    }

    @Test
    fun `verify fragment is setup correctly`() {
        fragmentScenario = launchFragmentInContainer<UserInfoFragment>().onFragment { fragment ->
            assertTrue(fragment.binding!!.recyclerView.adapter is RepositoryAdapter)
            assertEquals(1, fragment.binding!!.recyclerView.itemDecorationCount)
            assertTrue(fragment.binding!!.searchButton.hasOnClickListeners())
        }
    }

    @Test
    fun `verify user search result is consumed properly`() = runBlockingTest {
        val userName = "johnDoe"
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
        whenever(searchUserUseCase.execute(userName))
            .thenReturn(SearchUserUseCase.Result.Success(userEntity))
        whenever(fetchRepositoriesUseCase.execute(userName))
            .thenReturn(FetchRepositoriesUseCase.Result.Success(repoEntityList))

        fragmentScenario = launchFragmentInContainer<UserInfoFragment>().onFragment { fragment ->
            fragment.binding?.apply {
                assertNull(avatarImageView.drawable)
                assertTrue(nameTextView.text.isEmpty())
                assertTrue((recyclerView.adapter as RepositoryAdapter).getList().isEmpty())

                fragment.searchUser(userName)
                shadowOf(getMainLooper()).idle()

                assertTrue(avatarImageView.isVisible)
                assertTrue(nameTextView.isVisible)
                assertTrue(recyclerView.isVisible)

                assertEquals(Color.BLACK, (avatarImageView.drawable as ColorDrawable).color)
                assertEquals("displayName", nameTextView.text)
                assertEquals(repoEntityList, (recyclerView.adapter as RepositoryAdapter).getList())
            }
        }
    }

    @Test
    fun `verify nameTextView and avatarImageView are hidden if data is not valid`() =
        runBlockingTest {
            val userName = "johnDoe"
            var userEntity = UserEntity(
                id = "id",
                displayName = "",
                avatarUrl = "   "
            )

            whenever(searchUserUseCase.execute(userName))
                .thenReturn(SearchUserUseCase.Result.Success(userEntity))
            whenever(fetchRepositoriesUseCase.execute(userName))
                .thenReturn(FetchRepositoriesUseCase.Result.Success(emptyList()))

            fragmentScenario =
                launchFragmentInContainer<UserInfoFragment>().onFragment { fragment ->
                    fragment.binding?.apply {
                        fragment.searchUser(userName)
                        shadowOf(getMainLooper()).idle()

                        assertTrue(nameTextView.isGone)
                        assertTrue(avatarImageView.isGone)
                    }
                }

            userEntity = userEntity.copy(displayName = "    ")
            Coil.setImageLoader(getFakeErrorImageLoader())
            whenever(searchUserUseCase.execute(userName))
                .thenReturn(SearchUserUseCase.Result.Success(userEntity))

            fragmentScenario.onFragment { fragment ->
                fragment.binding?.apply {
                    fragment.searchUser(userName)
                    shadowOf(getMainLooper()).idle()

                    assertTrue(nameTextView.isGone)
                    assertTrue(avatarImageView.isGone)
                }
            }
        }

    @Test
    fun `verify clicking on a RepositoryEntity item loads RepoDetailDialog`() =
        runBlockingTest {
            val userName = "johnDoe"
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
            whenever(searchUserUseCase.execute(userName))
                .thenReturn(SearchUserUseCase.Result.Success(userEntity))
            whenever(fetchRepositoriesUseCase.execute(userName))
                .thenReturn(FetchRepositoriesUseCase.Result.Success(repoEntityList))

            fragmentScenario =
                launchFragmentInContainer<UserInfoFragment>().onFragment { fragment ->
                    fragment.binding?.apply {
                        fragment.searchUser(userName)
                        shadowOf(getMainLooper()).idle()
                        recyclerView[0].performClick()
                        shadowOf(getMainLooper()).idle()

                        assertTrue(fragment.activity!!.supportFragmentManager.fragments.last() is RepoDetailDialog)
                    }
                }
        }

    @Test
    fun `verify space decoration is properly applied to recyclerview items`() = runBlockingTest {
        val userName = "johnDoe"
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
            ),
            RepositoryEntity(
                "id1",
                name = "name",
                description = "description",
                lastUpdatedAt = "lastUpdatedAt",
                starredCount = 20,
                forkedCount = 20
            )
        )

        whenever(searchUserUseCase.execute(userName))
            .thenReturn(SearchUserUseCase.Result.Success(userEntity))
        whenever(fetchRepositoriesUseCase.execute(userName))
            .thenReturn(FetchRepositoriesUseCase.Result.Success(repoEntityList))

        fragmentScenario = launchFragmentInContainer<UserInfoFragment>().onFragment { fragment ->
            fragment.binding?.apply {
                fragment.searchUser(userName)
                shadowOf(getMainLooper()).idle()

                val decoration = recyclerView.getItemDecorationAt(0) as SpaceItemDecoration

                val firstItem = recyclerView.findViewHolderForAdapterPosition(0)
                val secondItem = recyclerView.findViewHolderForAdapterPosition(1)

                val rect = Rect()
                decoration.getItemOffsets(
                    rect,
                    firstItem!!.itemView,
                    recyclerView,
                    RecyclerView.State()
                )

                assertEquals(0, rect.left)
                assertEquals(16, rect.top)
                assertEquals(0, rect.right)
                assertEquals(0, rect.bottom)

                decoration.getItemOffsets(
                    rect,
                    secondItem!!.itemView,
                    recyclerView,
                    RecyclerView.State()
                )

                assertEquals(0, rect.left)
                assertEquals(16, rect.top)
                assertEquals(0, rect.right)
                assertEquals(0, rect.bottom)
            }
        }
    }

    private fun UserInfoFragment.searchUser(userKeyword: String?) {
        binding?.searchField?.editText?.setText(userKeyword)
        binding?.searchButton?.performClick()
    }

    private fun setupKoin() {
        loadKoinModules(
            module {
                single { mock<SearchUserUseCase>() }
                single { mock<FetchRepositoriesUseCase>() }
                single { (clickListener: ItemClickListener) -> RepositoryAdapter(clickListener) }
                viewModel { UserInfoViewModel(get(), get()) }
            }
        )
    }
}