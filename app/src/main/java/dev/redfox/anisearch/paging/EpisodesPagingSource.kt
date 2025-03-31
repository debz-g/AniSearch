package dev.redfox.anisearch.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.redfox.anisearch.models.Episode
import dev.redfox.anisearch.network.ServerInterface
import retrofit2.HttpException
import java.io.IOException

class EpisodesPagingSource(
    private val apiService: ServerInterface,
    private val animeId: Int?
) : PagingSource<Int, Episode>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Episode> {
        val page = params.key ?: 1

        return try {
            val response = apiService.getAnimeEpisodes(animeId!!, page)


            LoadResult.Page(
                data = response.data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.pagination.hasNextPage) page + 1 else null
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Episode>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}