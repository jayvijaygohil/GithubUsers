package com.jayvijaygohil.githubusers.common

import com.jayvijaygohil.domain.entity.RepositoryEntity

interface ItemClickListener {
    fun onItemClick(item: RepositoryEntity)
}