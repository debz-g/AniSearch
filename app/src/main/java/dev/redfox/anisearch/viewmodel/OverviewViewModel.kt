package dev.redfox.anisearch.viewmodel

import android.os.Bundle
import androidx.lifecycle.ViewModel
import dev.redfox.anisearch.models.OverviewData
import dev.redfox.anisearch.network.AnimeRepository
import dev.redfox.anisearch.network.RetrofitClient
import dev.redfox.anisearch.utils.PARAM_DATA
import dev.redfox.anisearch.utils.parcelable

class OverviewViewModel : ViewModel() {

    private val animeRepository: AnimeRepository by lazy {
        AnimeRepository(RetrofitClient.apiService)
    }

    private var overviewData: OverviewData? = null

    fun handleExtras(extras: Bundle?) {
        extras?.let { args ->
            args.parcelable<OverviewData>(PARAM_DATA)?.let { data ->
                overviewData = data
            }
        }
    }

    fun getOverviewData() = overviewData
}