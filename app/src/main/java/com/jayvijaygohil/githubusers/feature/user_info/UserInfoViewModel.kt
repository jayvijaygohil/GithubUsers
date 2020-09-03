package com.jayvijaygohil.githubusers.feature.user_info

import androidx.lifecycle.viewModelScope
import com.jayvijaygohil.domain.entity.RepositoryEntity
import com.jayvijaygohil.domain.entity.UserEntity
import com.jayvijaygohil.domain.usecase.FetchRepositoriesUseCase
import com.jayvijaygohil.domain.usecase.SearchUserUseCase
import com.jayvijaygohil.githubusers.common.BaseAction
import com.jayvijaygohil.githubusers.common.BaseViewModel
import com.jayvijaygohil.githubusers.common.BaseViewState
import kotlinx.coroutines.launch

class UserInfoViewModel(
    private val searchUserUseCase: SearchUserUseCase,
    private val fetchRepositoriesUseCase: FetchRepositoriesUseCase
) : BaseViewModel<UserInfoViewModel.ViewState, UserInfoViewModel.Action>(ViewState()) {

    override fun onReduceState(viewAction: Action) = when (viewAction) {
        is Action.UserInfoLoadingSuccess -> state.copy(
            isError = false,
            userInfo = viewAction.userInfo,
            repositories = viewAction.repoList
        )
        is Action.UserInfoLoadingFailure -> state.copy(
            isError = true,
            userInfo = null,
            repositories = emptyList()
        )
    }

    fun searchUser(searchKeyword: String?) = viewModelScope.launch {
        if (searchKeyword.isNullOrBlank()) return@launch

        var userInfoResult: UserEntity?
        var repositoryListResult: List<RepositoryEntity>

        searchUserUseCase.execute(searchKeyword).also { result ->
            userInfoResult = if (result is SearchUserUseCase.Result.Success) {
                result.data
            } else null
        }

        fetchRepositoriesUseCase.execute(searchKeyword).also { result ->
            repositoryListResult = if (result is FetchRepositoriesUseCase.Result.Success) {
                result.data
            } else emptyList()
        }

        val action = userInfoResult?.let {
            Action.UserInfoLoadingSuccess(
                userInfo = it,
                repoList = repositoryListResult,
            )
        } ?: Action.UserInfoLoadingFailure

        sendAction(action)
    }

    data class ViewState(
        val isError: Boolean = false,
        val userInfo: UserEntity? = null,
        val repositories: List<RepositoryEntity> = emptyList()
    ) : BaseViewState

    sealed class Action : BaseAction {
        class UserInfoLoadingSuccess(
            val userInfo: UserEntity,
            val repoList: List<RepositoryEntity>
        ) : Action()

        object UserInfoLoadingFailure : Action()
    }
}