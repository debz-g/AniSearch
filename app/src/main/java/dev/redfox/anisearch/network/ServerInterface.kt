package dev.redfox.anisearch.network

import retrofit2.http.GET
import dev.redfox.anisearch.models.TopApiDataClass.TopAnimeResponse
import retrofit2.Response
import retrofit2.http.Query

interface ServerInterface {
	@GET("top/anime")
	suspend fun getTopAnime(@Query("page") page: Int) : TopAnimeResponse
}
