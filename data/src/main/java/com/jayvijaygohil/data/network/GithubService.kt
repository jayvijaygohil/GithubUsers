package com.jayvijaygohil.data.network

import com.jayvijaygohil.data.model.RepositoryModel
import com.jayvijaygohil.data.model.UserModel
import retrofit2.http.GET
import retrofit2.http.Path

internal interface GithubService {
    @GET("users/{username}")
    suspend fun searchUser(
        @Path("username") userName: String
    ): UserModel?

    @GET("users/{username}/repos")
    suspend fun fetchRepositories(
        @Path("username") userName: String
    ): List<RepositoryModel>?
}