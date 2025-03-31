package dev.redfox.anisearch.models

import com.google.gson.annotations.SerializedName

data class EpisodeResponse(
    val pagination: EpisodesPagination,
    val data: List<Episode>
)

data class EpisodesPagination(
    @SerializedName("last_visible_page")
    val lastVisiblePage: Int,
    @SerializedName("has_next_page")
    val hasNextPage: Boolean
)

data class Episode(
    @SerializedName("mal_id")
    val malId: Int,
    val url: String,
    val title: String,
    @SerializedName("title_japanese")
    val titleJapanese: String?,
    @SerializedName("title_romanji")
    val titleRomanji: String?,
    val aired: String?,
    val score: Double?,
    val filler: Boolean,
    val recap: Boolean,
    @SerializedName("forum_url")
    val forumUrl: String?
)
