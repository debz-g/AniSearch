package dev.redfox.anisearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.redfox.anisearch.models.AnimeData
import dev.redfox.anisearch.network.AnimeRepository
import dev.redfox.anisearch.network.RetrofitClient

class TopAnimeViewModel() : ViewModel() {

    private val animeRepository: AnimeRepository by lazy {
        AnimeRepository(RetrofitClient.apiService)
    }

    val topAnime: LiveData<PagingData<AnimeData>> =
        animeRepository.getTopAnimePaged().cachedIn(viewModelScope)

}
