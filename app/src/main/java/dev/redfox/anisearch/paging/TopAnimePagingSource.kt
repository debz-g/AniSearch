package dev.redfox.anisearch.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.redfox.anisearch.models.TopApiDataClass
import dev.redfox.anisearch.network.ServerInterface

class TopAnimePagingSource(
    private val apiService: ServerInterface
) : PagingSource<Int, TopApiDataClass.AnimeData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TopApiDataClass.AnimeData> {
        val page = params.key ?: 1
        return try {
            // Directly fetch data without Response<T> wrapping
            val response = apiService.getTopAnime(page)
            val animeList = response.data ?: emptyList()

            LoadResult.Page(
                data = animeList,
                prevKey = if (page == 1) null else page - 1,  // Previous page
                nextKey = if (response.pagination.hasNextPage) page + 1 else null // Next page
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TopApiDataClass.AnimeData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
