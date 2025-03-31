package dev.redfox.anisearch.models

import com.google.gson.annotations.SerializedName


data class CharactersResponse(
    val data: List<CharacterData>
)

data class CharacterData(
    val character: Character,
    val role: String,
    val favorites: Int,
    @SerializedName("voice_actors")
    val voiceActors: List<VoiceActor>
)

data class Character(
    @SerializedName("mal_id")
    val malId: Int,
    val url: String,
    val images: CharacterImages,
    val name: String
)

data class CharacterImages(
    val jpg: CharacterImageUrl,
    val webp: CharacterImageUrlWebp
)

data class CharacterImageUrl(
    @SerializedName("image_url")
    val imageUrl: String
)

data class CharacterImageUrlWebp(
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("small_image_url")
    val smallImageUrl: String
)

data class VoiceActor(
    val person: Person,
    val language: String
)

data class Person(
    @SerializedName("mal_id")
    val malId: Int,
    val url: String,
    val images: PersonImages,
    val name: String
)

data class PersonImages(
    val jpg: PersonImageUrl
)

data class PersonImageUrl(
    @SerializedName("image_url")
    val imageUrl: String
)