package dev.redfox.anisearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.redfox.anisearch.models.CommonApiDataClass
import dev.redfox.anisearch.network.AnimeRepository

class TopAnimeViewModel(private val repository: AnimeRepository) : ViewModel() {

    val topAnime: LiveData<PagingData<CommonApiDataClass.AnimeData>> =
        repository.getTopAnimePaged().cachedIn(viewModelScope)

    class Factory(private val repository: AnimeRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TopAnimeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TopAnimeViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
