package dev.redfox.anisearch.ui.news.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.redfox.anisearch.databinding.FragmentNewsAnimeBinding

class NewsAnimeFragment : Fragment() {
    private val binding: FragmentNewsAnimeBinding by lazy {
        FragmentNewsAnimeBinding.inflate(layoutInflater)
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