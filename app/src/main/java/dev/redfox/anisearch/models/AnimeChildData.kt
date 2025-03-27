package dev.redfox.anisearch.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnimeChildData(
    val photo: String,
    val title: String,
    val description: String? = null,
    val status: String,
    val studio: String? = null,
    val totalEpisodes: Int,
    val currentEpisode: Int,
    val linkMAL: String,
    val genre: ArrayList<String>
): Parcelable
