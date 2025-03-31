package dev.redfox.anisearch.network

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import dev.redfox.anisearch.models.AnimeData
import dev.redfox.anisearch.models.CharactersResponse
import dev.redfox.anisearch.models.Episode
import dev.redfox.anisearch.paging.EpisodesPagingSource
import dev.redfox.anisearch.paging.SchedulePagingSource
import dev.redfox.anisearch.paging.TopAnimePagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Response

class AnimeRepository(private val apiService: ServerInterface) {

    fun getTopAnimePaged(): LiveData<PagingData<AnimeData>> = Pager(
        config = PagingConfig(
            pageSize = 10, // Adjust page size as needed
            enablePlaceholders = false
        ),
        pagingSourceFactory = { TopAnimePagingSource(apiService) }
    ).liveData

    fun getSchedule(day: String): Flow<PagingData<AnimeData>> = Pager(
        config = PagingConfig(
            pageSize = 25,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { SchedulePagingSource(day, apiService) }
    ).flow

    fun getAnimeEpisodes(animeId: Int?): Flow<PagingData<Episode>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { EpisodesPagingSource(apiService, animeId) }
    ).flow

    suspend fun getAnimeCharacters(animeId: Int): Response<CharactersResponse> {
        return withContext(Dispatchers.IO) {
            apiService.getAnimeCharacters(animeId)
        }
    }
}


/*@OptIn(ExperimentalPagingApi::class)
class TopAnimeRepository(
    private val apiService: ServerInterface,
    private val database: AnimeDatabase
) {
    fun getTopAnimePaged(): LiveData<PagingData<AnimeEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            remoteMediator = AnimeRemoteMediator(apiService, database),
            pagingSourceFactory = { database.animeDao().getPagedAnime() }
        ).liveData
    }
}*/
