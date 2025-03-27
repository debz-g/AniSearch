package dev.redfox.anisearch.ui.customViews

import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.redfox.anisearch.databinding.DialogAnimeDetailsFragmentBinding
import dev.redfox.anisearch.models.AnimeChildData
import dev.redfox.anisearch.utils.Constants

class AnimeDetailsBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var mContext: Context

    private val binding: DialogAnimeDetailsFragmentBinding by lazy {
        DialogAnimeDetailsFragmentBinding.inflate(layoutInflater)
    }

    companion object {
        fun getInstance(animeChildData: AnimeChildData) : AnimeDetailsBottomSheetFragment {
            val fragment = AnimeDetailsBottomSheetFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(Constants.PARAM_DATA, animeChildData)
            }
            return fragment
        }
    }
}

/**
 * Anime Data to be displayed:
 * Photo, Name, Synopsis, Status, Producers/Studio, Episodes, MAL Link, Genre
 * */