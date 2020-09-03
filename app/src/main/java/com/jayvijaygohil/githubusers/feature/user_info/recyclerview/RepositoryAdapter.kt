package com.jayvijaygohil.githubusers.feature.user_info.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jayvijaygohil.domain.entity.RepositoryEntity
import com.jayvijaygohil.githubusers.common.ItemClickListener
import com.jayvijaygohil.githubusers.databinding.ItemRepositoryBinding

class RepositoryAdapter(
    private val clickListener: ItemClickListener
) : RecyclerView.Adapter<RepositoryViewHolder>() {
    private val diffUtil =
        AsyncListDiffer(this, object : DiffUtil.ItemCallback<RepositoryEntity>() {
            override fun areItemsTheSame(
                oldItem: RepositoryEntity,
                newItem: RepositoryEntity
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: RepositoryEntity,
                newItem: RepositoryEntity
            ): Boolean =
                (oldItem.name == newItem.name && oldItem.description == oldItem.description)
        })

    init {
        diffUtil.submitList(emptyList())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val binding = ItemRepositoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return RepositoryViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(diffUtil.currentList[position])
    }

    override fun getItemCount(): Int = diffUtil.currentList.size

    fun submitList(items: List<RepositoryEntity>) {
        diffUtil.submitList(items)
    }

    fun getList() = diffUtil.currentList
}