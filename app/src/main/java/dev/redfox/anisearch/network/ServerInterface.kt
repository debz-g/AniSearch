package dev.redfox.anisearch.network

import dev.redfox.anisearch.models.CommonAnimeResponse
import dev.redfox.anisearch.models.EpisodeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServerInterface {
	@GET("top/anime")
	suspend fun getTopAnime(@Query("page") page: Int) : CommonAnimeResponse

	@GET("schedules")
	suspend fun getSchedule(
		@Query("filter") day: String,
		@Query("page") page: Int
	): CommonAnimeResponse

	@GET("anime/{id}/episodes")
	suspend fun getAnimeEpisodes(
		@Path("id") animeId: Int,
		@Query("page") page: Int
	): EpisodeResponse
}
