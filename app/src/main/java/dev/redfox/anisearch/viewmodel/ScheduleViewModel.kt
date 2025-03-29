package dev.redfox.anisearch.viewmodel

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.redfox.anisearch.models.AnimeData
import dev.redfox.anisearch.network.AnimeRepository
import dev.redfox.anisearch.network.RetrofitClient
import dev.redfox.anisearch.utils.PARAM_ARG_1
import kotlinx.coroutines.flow.Flow

class ScheduleViewModel() : ViewModel() {

    private val animeRepository: AnimeRepository by lazy {
        AnimeRepository(RetrofitClient.apiService)
    }

    private var day: String = "monday"

    fun handleExtras(extras: Bundle?): Boolean {
        return extras?.let { args ->
            args.getString(PARAM_ARG_1)?.let {
                day = it
            }
            true
        } ?: false
    }

    fun getDay() = day

    fun getSchedule(day: String): Flow<PagingData<AnimeData>> {
        return animeRepository.getSchedule(day).cachedIn(viewModelScope)
    }

}