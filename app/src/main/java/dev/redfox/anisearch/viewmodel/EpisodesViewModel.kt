package dev.redfox.anisearch.viewmodel

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.redfox.anisearch.models.Episode
import dev.redfox.anisearch.network.AnimeRepository
import dev.redfox.anisearch.network.RetrofitClient
import dev.redfox.anisearch.utils.PARAM_ID
import kotlinx.coroutines.flow.Flow

class EpisodesViewModel : ViewModel() {
    private val animeRepository: AnimeRepository by lazy {
        AnimeRepository(RetrofitClient.apiService)
    }

    private var malId: Int? = 0

    fun handleExtras(extras: Bundle?) {
        extras?.let { args ->
            args.getInt(PARAM_ID).let { id ->
                malId = id
            }
        }
    }

    fun getMalId() = malId

    fun getEpisodes(malId: Int?): Flow<PagingData<Episode>> {
        return animeRepository.getAnimeEpisodes(malId).cachedIn(viewModelScope)
    }
}