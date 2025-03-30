package dev.redfox.anisearch.ui.topAnime.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dev.redfox.anisearch.MainActivity
import dev.redfox.anisearch.R
import dev.redfox.anisearch.databinding.FragmentTopAnimeBinding
import dev.redfox.anisearch.ui.animeDetails.AnimeDetailsBottomSheetFragment
import dev.redfox.anisearch.ui.topAnime.adapter.TopAnimeAdapter
import dev.redfox.anisearch.utils.animeDetailsDialogTag
import dev.redfox.anisearch.utils.changeBackgroundDrawableColor
import dev.redfox.anisearch.utils.getColorCompat
import dev.redfox.anisearch.utils.getModelView
import dev.redfox.anisearch.utils.hide
import dev.redfox.anisearch.utils.show
import dev.redfox.anisearch.utils.showLog
import dev.redfox.anisearch.viewmodel.TopAnimeViewModel
import kotlinx.coroutines.launch

class TopAnimeFragment : Fragment() {

    private val binding: FragmentTopAnimeBinding by lazy {
        FragmentTopAnimeBinding.inflate(layoutInflater)
    }

    private lateinit var mContext: Context
    private lateinit var parentActivity: MainActivity
    private lateinit var viewModel: TopAnimeViewModel
    private lateinit var animeAdapter: TopAnimeAdapter

    companion object {
        fun getInstance() = TopAnimeFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        parentActivity = mContext as MainActivity
        viewModel = getModelView(TopAnimeViewModel()) as TopAnimeViewModel
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
        setStatusBarInsets()
        binding.apply {
            progressBar.show()
            rvAnimeList.hide()
            ablExplore.bringToFront()
            exploreStatusBarPlaceholder.bringToFront()
            exploreStatusBarPlaceholder.changeBackgroundDrawableColor(getColorCompat(R.color.colorBlack100))
        }
        setupRecyclerView()
        observeAnimeData()
    }

    private fun setStatusBarInsets() {
        binding.root.apply {
            ViewCompat.setOnApplyWindowInsetsListener(this) { v, windowInsets ->
                val statusBarHeight =
                    windowInsets.getInsets(WindowInsetsCompat.Type.statusBars()).top
                binding.exploreStatusBarPlaceholder.apply {
                    layoutParams = layoutParams.apply { height = statusBarHeight }
                }
                windowInsets
            }
        }
    }

    private fun setupRecyclerView() {
        animeAdapter = TopAnimeAdapter(onItemClick = { animeChildData ->
            AnimeDetailsBottomSheetFragment.getInstance(
                animeChildData
            ).show(
                childFragmentManager,
                animeDetailsDialogTag
            )
        })
        binding.rvAnimeList.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(mContext, 2) // 2 columns
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

    fun scrollToTop() {
        try {
            binding.rvAnimeList.smoothScrollToPosition(0)
        } catch (ex: Exception) {
            ex.showLog()
        }
    }
}
