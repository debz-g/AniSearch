package dev.redfox.anisearch.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OverviewData(
    val aired: String?,
    val duration: String?,
    val episodes: Int?,
    val members: Int?,
    val rank: Int?,
    val favourites: Int?,
    val tags: List<String>?,
    val producers: List<String>?,
    val studio: String? = null
) : Parcelable