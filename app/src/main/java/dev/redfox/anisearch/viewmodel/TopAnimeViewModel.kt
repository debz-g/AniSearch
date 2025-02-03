package dev.redfox.anisearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.redfox.anisearch.models.TopApiDataClass.AnimeData
import dev.redfox.anisearch.network.TopAnimeRepository

class TopAnimeViewModel(private val repository: TopAnimeRepository) : ViewModel() {

    val topAnime: LiveData<PagingData<AnimeData>> =
        repository.getTopAnimePaged().cachedIn(viewModelScope)

    class Factory(private val repository: TopAnimeRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TopAnimeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TopAnimeViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
