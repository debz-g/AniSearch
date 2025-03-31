package dev.redfox.anisearch.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.redfox.anisearch.models.CharacterData
import dev.redfox.anisearch.network.AnimeRepository
import dev.redfox.anisearch.network.RetrofitClient
import dev.redfox.anisearch.utils.PARAM_ID
import kotlinx.coroutines.launch

class CharactersViewModel : ViewModel() {
    private val animeRepository: AnimeRepository by lazy {
        AnimeRepository(RetrofitClient.apiService)
    }

    private var malId: Int? = 0

    private val _characters = MutableLiveData<List<CharacterData>>()
    val characters: LiveData<List<CharacterData>> = _characters

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun handleExtras(extras: Bundle?) {
        extras?.let { args ->
            args.getInt(PARAM_ID).let { id ->
                malId = id
                fetchCharacters()
            }
        }
    }

    fun fetchCharacters() {
        malId?.let { id ->
            viewModelScope.launch {
                try {
                    _isLoading.value = true
                    _error.value = null

                    val response = animeRepository.getAnimeCharacters(id)
                    if (response.isSuccessful) {
                        response.body()?.let { charactersResponse ->
                            _characters.value = charactersResponse.data
                        }
                    } else {
                        _error.value = "Failed to fetch characters: ${response.message()}"
                    }
                } catch (e: Exception) {
                    _error.value = "Error fetching characters: ${e.message}"
                } finally {
                    _isLoading.value = false
                }
            }
        } ?: run {
            _error.value = "Invalid anime ID"
        }
    }

    fun getMalId() = malId

    fun clearError() {
        _error.value = null
    }
}