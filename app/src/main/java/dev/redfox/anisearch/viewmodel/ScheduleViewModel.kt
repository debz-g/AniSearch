package dev.redfox.anisearch.viewmodel

import android.os.Bundle
import androidx.lifecycle.ViewModel
import dev.redfox.anisearch.network.AnimeRepository
import dev.redfox.anisearch.network.RetrofitClient
import dev.redfox.anisearch.utils.Constants

class ScheduleViewModel() : ViewModel() {

    private val animeRepository: AnimeRepository by lazy {
        AnimeRepository(RetrofitClient.apiService)
    }

    private var day: String = "monday"

    fun handleExtras(extras: Bundle?): Boolean {
        return extras?.let { args ->
            args.getString(Constants.PARAM_ARG_1)?.let {
                day = it
            }
            true
        } ?: false
    }

    fun getDay() = day

}