package com.jayvijaygohil.githubusers.feature.user_info

import android.animation.AnimatorSet
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import coil.load
import com.jayvijaygohil.domain.entity.RepositoryEntity
import com.jayvijaygohil.githubusers.R
import com.jayvijaygohil.githubusers.common.*
import com.jayvijaygohil.githubusers.databinding.FragmentUserInfoBinding
import com.jayvijaygohil.githubusers.feature.repository_detail.RepoDetailDialog
import com.jayvijaygohil.githubusers.feature.user_info.recyclerview.RepositoryAdapter
import com.jayvijaygohil.githubusers.feature.user_info.recyclerview.SpaceItemDecoration
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class UserInfoFragment : Fragment(R.layout.fragment_user_info), ItemClickListener {

    companion object {
        private const val ANIMATION_TIME_IN_MILLI = 1000L
    }

    var binding: FragmentUserInfoBinding? = null
        private set

    private val adapter: RepositoryAdapter by inject { parametersOf(this as ItemClickListener) }
    private val viewModel: UserInfoViewModel by inject()

    private val stateObserver = Observer<UserInfoViewModel.ViewState> {
        bindDisplayName(it.userInfo?.displayName)
        bindAvatar(it.userInfo?.avatarUrl)
        bindRepos(it.repositories)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentUserInfoBinding.bind(view)

        binding?.apply {
            recyclerView.adapter = adapter
            context?.let { recyclerView.addItemDecoration(SpaceItemDecoration(it)) }
            searchButton.setOnClickListener {
                val searchKeyword = searchField.editText?.text?.toString()
                viewModel.searchUser(searchKeyword)
            }
        }

        observe(viewModel.stateLiveData, stateObserver)
    }

    override fun onItemClick(item: RepositoryEntity) {
        activity?.supportFragmentManager?.let {
            RepoDetailDialog.newInstance(item).show(it, RepoDetailDialog::class.simpleName)
        }
    }

    private fun bindDisplayName(displayName: String?) {
        val textView = binding?.nameTextView ?: return

        textView.setTextVisibility(displayName)
        textView.slideInUp(AnimatorSet(), ANIMATION_TIME_IN_MILLI)
    }

    private fun bindAvatar(avatarUrl: String?) {
        val imageView = binding?.avatarImageView ?: return

        if (avatarUrl.isNullOrBlank()) {
            imageView.hide()
            return
        }

        imageView.load(avatarUrl) {
            target(
                onError = {
                    imageView.hide()
                },
                onSuccess = {
                    imageView.setImageDrawable(it)
                    imageView.slideInUp(AnimatorSet(), ANIMATION_TIME_IN_MILLI)
                }
            )
        }
    }

    private fun bindRepos(list: List<RepositoryEntity>) {
        adapter.submitList(list)
        binding?.recyclerView?.slideInUp(AnimatorSet(), ANIMATION_TIME_IN_MILLI)
    }
}