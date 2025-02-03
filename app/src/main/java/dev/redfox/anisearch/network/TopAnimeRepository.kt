package dev.redfox.anisearch.network

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import dev.redfox.anisearch.models.TopApiDataClass.AnimeData
import dev.redfox.anisearch.paging.TopAnimePagingSource

class TopAnimeRepository(private val apiService: ServerInterface) {

    fun getTopAnimePaged(): LiveData<PagingData<AnimeData>> = Pager(
        config = PagingConfig(
            pageSize = 10, // Adjust page size as needed
            enablePlaceholders = false
        ),
        pagingSourceFactory = { TopAnimePagingSource(apiService) }
    ).liveData
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
