package dev.redfox.anisearch.paging.caching

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AnimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(animeList: List<AnimeEntity>)

    @Query("SELECT * FROM anime_table Order by createdAt ASC")
    fun getPagedAnime(): PagingSource<Int, AnimeEntity>

    @Query("DELETE FROM anime_table")
    suspend fun clearAll()
}