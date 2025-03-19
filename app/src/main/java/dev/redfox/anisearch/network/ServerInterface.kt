package dev.redfox.anisearch.network

import dev.redfox.anisearch.models.CommonApiDataClass
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerInterface {
	@GET("top/anime")
	suspend fun getTopAnime(@Query("page") page: Int) : CommonApiDataClass.CommonAnimeResponse
}
