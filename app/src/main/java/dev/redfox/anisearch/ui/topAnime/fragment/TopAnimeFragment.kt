package dev.redfox.anisearch.ui.topAnime.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dev.redfox.anisearch.databinding.FragmentTopAnimeBinding
import dev.redfox.anisearch.network.RetrofitClient
import dev.redfox.anisearch.network.TopAnimeRepository
import dev.redfox.anisearch.paging.caching.AnimeDatabase
import dev.redfox.anisearch.ui.topAnime.adapter.TopAnimeAdapter
import dev.redfox.anisearch.utils.hide
import dev.redfox.anisearch.utils.show
import dev.redfox.anisearch.viewmodel.TopAnimeViewModel
import kotlinx.coroutines.launch

class TopAnimeFragment : Fragment() {

    private val binding: FragmentTopAnimeBinding by lazy {
        FragmentTopAnimeBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: TopAnimeViewModel
    private val animeAdapter = TopAnimeAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            progressBar.show()
            rvAnimeList.hide()
        }
        val repository = TopAnimeRepository(RetrofitClient.apiService)

        viewModel = ViewModelProvider(
            this,
            TopAnimeViewModel.Factory(repository)
        )[TopAnimeViewModel::class.java]

        setupRecyclerView()
        observeAnimeData()
    }

    private fun setupRecyclerView() {
        binding.rvAnimeList.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 2) // 2 columns
            adapter = animeAdapter
        }
    }

    private fun observeAnimeData() {
        lifecycleScope.launch {
            viewModel.topAnime.observe(viewLifecycleOwner) { pagingData ->
                binding.apply {
                    progressBar.hide()
                    rvAnimeList.show()
                }
                animeAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
            }
        }
    }
}
