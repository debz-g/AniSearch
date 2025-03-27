package dev.redfox.anisearch.network

import dev.redfox.anisearch.models.CommonAnimeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerInterface {
	@GET("seasons/now")
	suspend fun getTopAnime(@Query("page") page: Int) : CommonAnimeResponse

	@GET("schedules")
	suspend fun getSchedule(
		@Query("filter") day: String,
		@Query("page") page: Int
	): CommonAnimeResponse
}
