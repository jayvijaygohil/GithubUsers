package com.jayvijaygohil.githubusers.feature.repository_detail

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.fragment.app.DialogFragment
import com.jayvijaygohil.domain.entity.RepositoryEntity
import com.jayvijaygohil.githubusers.common.setTextVisibility
import com.jayvijaygohil.githubusers.databinding.DialogRepoDetailBinding

class RepoDetailDialog(private val item: RepositoryEntity) : DialogFragment() {

    companion object {
        fun newInstance(item: RepositoryEntity) = RepoDetailDialog(item)
    }

    private var binding: DialogRepoDetailBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogRepoDetailBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lastUpdated.setTextVisibility(item.lastUpdatedAt)
            starsCount.setTextVisibility(item.starredCount.toString())
            forksCount.setTextVisibility(item.forkedCount.toString())
        }
    }

    override fun onResume() {
        super.onResume()

        dialog?.window?.apply {
            setGravity(Gravity.BOTTOM)
            setLayout(MATCH_PARENT, WRAP_CONTENT)
        }
    }
}