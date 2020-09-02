package com.jayvijaygohil.data.network

import com.jayvijaygohil.data.model.RepositoryModel
import com.jayvijaygohil.data.model.UserModel
import retrofit2.http.POST
import retrofit2.http.Path

internal interface GithubService {
    @POST("/users/{UserName}")
    suspend fun searchUser(
        @Path("UserName") userName: String
    ): UserModel?

    @POST("/users/{UserName}/repos")
    suspend fun fetchRepositories(
        @Path("UserName") userName: String
    ): List<RepositoryModel>?
}