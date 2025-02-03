package dev.redfox.anisearch.paging.caching

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import dev.redfox.anisearch.network.ServerInterface

@OptIn(ExperimentalPagingApi::class)
class AnimeRemoteMediator(
    private val apiService: ServerInterface,
    private val database: AnimeDatabase
) : RemoteMediator<Int, AnimeEntity>() {

    private val animeDao = database.animeDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AnimeEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                lastItem?.malId ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
        }

        return try {
            val response = apiService.getTopAnime(page)
            val animeList = response.data.map {
                AnimeEntity(
                    malId = it.malId,
                    title = it.title,
                    titleEnglish = it.titleEnglish,
                    imageUrl = it.images.jpg.imageUrl ?: "",
                    score = it.score
                )
            }

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    animeDao.clearAll()
                }
                animeDao.insertAll(animeList)
            }

            MediatorResult.Success(endOfPaginationReached = response.pagination.hasNextPage.not())

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}