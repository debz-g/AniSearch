package dev.redfox.anisearch.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnimeChildData(
    val malId: Int,
    val image: String? = null,
    val title: String,
    val description: String? = null,
    val linkMAL: String,
    val linkTrailer: String? = null,
    val englishTitle: String? = null,
    val japaneseTitle: String? = null,
    val genre: List<String>
): Parcelable
