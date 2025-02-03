package dev.redfox.anisearch.ui.favourite.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.redfox.anisearch.databinding.FragmentFavouriteAnimeBinding

class FavouriteAnimeFragment : Fragment() {
    private val binding: FragmentFavouriteAnimeBinding by lazy {
        FragmentFavouriteAnimeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}