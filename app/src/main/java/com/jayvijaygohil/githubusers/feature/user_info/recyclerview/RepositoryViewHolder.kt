package com.jayvijaygohil.githubusers.feature.user_info.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.jayvijaygohil.domain.entity.RepositoryEntity
import com.jayvijaygohil.githubusers.common.ItemClickListener
import com.jayvijaygohil.githubusers.common.setTextVisibility
import com.jayvijaygohil.githubusers.databinding.ItemRepositoryBinding

class RepositoryViewHolder(
    val binding: ItemRepositoryBinding,
    private val clickListener: ItemClickListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: RepositoryEntity) {
        binding.root.setOnClickListener { clickListener.onItemClick(item) }

        binding.repositoryTitle.setTextVisibility(item.name)
        binding.repositoryDescription.setTextVisibility(item.description)
    }
}