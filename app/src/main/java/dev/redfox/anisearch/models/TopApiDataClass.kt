package dev.redfox.anisearch.models

import com.google.gson.annotations.SerializedName

class TopApiDataClass {
    data class TopAnimeResponse(
        val pagination: Pagination,
        val data: List<AnimeData>
    )

    data class Pagination(
        @SerializedName("last_visible_page")
        val lastVisiblePage: Int,

        @SerializedName("has_next_page")
        val hasNextPage: Boolean,

        @SerializedName("current_page")
        val currentPage: Int,

        val items: PaginationItems
    )

    data class PaginationItems(
        val count: Int,
        val total: Int,

        @SerializedName("per_page")
        val perPage: Int
    )

    /**
     * Each element inside the "data" array.
     * This has many fields; adapt/omit fields as needed for your use case.
     */
    data class AnimeData(
        @SerializedName("mal_id")
        val malId: Int,
        val url: String,
        val images: AnimeImages,
        val trailer: Trailer?,

        val approved: Boolean,
        val titles: List<AnimeTitle>,

        val title: String,
        @SerializedName("title_english")
        val titleEnglish: String?,
        @SerializedName("title_japanese")
        val titleJapanese: String?,
        @SerializedName("title_synonyms")
        val titleSynonyms: List<String>,

        val type: String?,
        val source: String?,
        val episodes: Int?,
        val status: String?,
        val airing: Boolean?,

        val aired: Aired,
        val duration: String?,
        val rating: String?,

        val score: Double?,
        @SerializedName("scored_by")
        val scoredBy: Int?,
        val rank: Int?,
        val popularity: Int?,
        val members: Int?,
        val favorites: Int?,

        val synopsis: String?,
        val background: String?,

        val season: String?,
        val year: Int?,

        val broadcast: Broadcast?,

        val producers: List<GenericMalItem>,
        val licensors: List<GenericMalItem>,
        val studios: List<GenericMalItem>,
        val genres: List<GenericMalItem>,

        @SerializedName("explicit_genres")
        val explicitGenres: List<GenericMalItem>,

        val themes: List<GenericMalItem>,
        val demographics: List<GenericMalItem>
    )

    /** Title objects inside the 'titles' array. */
    data class AnimeTitle(
        val type: String,
        val title: String
    )

    /**
     * Holds both .jpg and .webp URLs.
     * You can extend to include all fields (small_image_url, etc.)
     * if needed.
     */
    data class AnimeImages(
        val jpg: AnimeImageUrls,
        val webp: AnimeImageUrls
    )

    data class AnimeImageUrls(
        @SerializedName("image_url")
        val imageUrl: String?,
        @SerializedName("small_image_url")
        val smallImageUrl: String?,
        @SerializedName("large_image_url")
        val largeImageUrl: String?
    )

    data class Trailer(
        @SerializedName("youtube_id")
        val youtubeId: String?,
        val url: String?,
        @SerializedName("embed_url")
        val embedUrl: String?,
        val images: TrailerImages?
    )

    data class TrailerImages(
        @SerializedName("image_url")
        val imageUrl: String?,
        @SerializedName("small_image_url")
        val smallImageUrl: String?,
        @SerializedName("medium_image_url")
        val mediumImageUrl: String?,
        @SerializedName("large_image_url")
        val largeImageUrl: String?,
        @SerializedName("maximum_image_url")
        val maximumImageUrl: String?
    )

    /**
     * For the 'aired' field, which contains date strings and a nested 'prop' object.
     */
    data class Aired(
        val from: String?,
        val to: String?,
        val prop: AiredProp,
        val string: String?
    )

    data class AiredProp(
        val from: AiredDate,
        val to: AiredDate
    )

    data class AiredDate(
        val day: Int?,
        val month: Int?,
        val year: Int?
    )

    /** For broadcasting info about day/time/timezone. */
    data class Broadcast(
        val day: String?,
        val time: String?,
        val timezone: String?,
        val string: String?
    )

    /**
     * Many fields (like producers, licensors, studios, genres) share the same structure:
     * { "mal_id": Int, "type": String, "name": String, "url": String }
     */
    data class GenericMalItem(
        @SerializedName("mal_id")
        val malId: Int?,
        val type: String?,
        val name: String?,
        val url: String?
    )

}
