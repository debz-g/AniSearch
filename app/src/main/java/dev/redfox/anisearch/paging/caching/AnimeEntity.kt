package dev.redfox.anisearch.paging.caching

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "anime_table")
data class AnimeEntity(
    @PrimaryKey
    @SerializedName("mal_id") val malId: Int,
    val createdAt: Long = System.currentTimeMillis(),
    val title: String,
    @SerializedName("title_english") val titleEnglish: String?,
    val imageUrl: String,
    val score: Double?,
)