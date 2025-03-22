package dev.redfox.anisearch.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.redfox.anisearch.models.AnimeData
import dev.redfox.anisearch.network.ServerInterface

class SchedulePagingSource(
    private val day: String,
    private val apiService: ServerInterface
) : PagingSource<Int, AnimeData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeData> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getSchedule(day, page)

            LoadResult.Page(
                data = response.data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.pagination.hasNextPage) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, AnimeData>): Int? {
        return state.anchorPosition?.let { state.closestPageToPosition(it)?.prevKey?.plus(1) }
    }
}