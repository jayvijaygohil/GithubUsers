package com.jayvijaygohil.data.model

import com.google.gson.annotations.SerializedName
import com.jayvijaygohil.domain.entity.RepositoryEntity
import java.text.SimpleDateFormat
import java.util.*

data class RepositoryModel(
    val id: Int,
    val name: String,
    val description: String?,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("stargazers_count") val stargazersCount: Int,
    @SerializedName("forks_count") val forksCount: Int
)

fun RepositoryModel.toDomainModel() = RepositoryEntity(
    id = id.toString(),
    name = name,
    description = description,
    lastUpdatedAt = convertDateTimeStyle(updatedAt),
    starredCount = stargazersCount,
    forkedCount = forksCount
)

private fun convertDateTimeStyle(dateTimeString: String): String {
    runCatching {
        val serverDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }.parse(dateTimeString)

        return SimpleDateFormat("MMM d, yyyy h:mm:ss a", Locale.US).apply {
            timeZone = TimeZone.getDefault()
        }.format(serverDateFormat).toString()
    }.getOrElse {
        return dateTimeString
    }
}