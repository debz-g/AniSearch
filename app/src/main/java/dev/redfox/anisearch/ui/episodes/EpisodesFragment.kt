package dev.redfox.anisearch.ui.episodes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import dev.redfox.anisearch.R
import dev.redfox.anisearch.databinding.FragmentEpisodesBinding
import dev.redfox.anisearch.ui.episodes.adapter.EpisodesAdapter
import dev.redfox.anisearch.utils.PARAM_ID
import dev.redfox.anisearch.utils.getDimensionPxSize
import dev.redfox.anisearch.utils.getModelView
import dev.redfox.anisearch.utils.setMargins
import dev.redfox.anisearch.viewmodel.EpisodesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EpisodesFragment : Fragment() {

    private val binding: FragmentEpisodesBinding by lazy {
        FragmentEpisodesBinding.inflate(layoutInflater)
    }
    private lateinit var mContext: Context
    private lateinit var episodesVm: EpisodesViewModel
    private lateinit var episodesAdapter: EpisodesAdapter

    companion object {
        fun getInstance(malId: Int): EpisodesFragment {
            val fragment = EpisodesFragment()
            fragment.arguments = Bundle().apply {
                putInt(PARAM_ID, malId)
            }
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        episodesVm = getModelView(EpisodesViewModel()) as EpisodesViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        episodesVm.handleExtras(arguments)
        setupRecyclerView()
        initObservers()
    }

    private fun setupRecyclerView() {
        episodesAdapter = EpisodesAdapter(mContext)
        binding.rvEpisodes.apply {
            setHasFixedSize(false)
            val horizontalMargin = mContext.getDimensionPxSize(R.dimen.dimen16)
            setMargins(left = horizontalMargin, right = horizontalMargin)
            layoutManager = LinearLayoutManager(mContext)
            isNestedScrollingEnabled = false
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            adapter = episodesAdapter
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            episodesVm.apply {
                getEpisodes(getMalId()).collectLatest { episodeData ->
                    episodesAdapter.submitData(episodeData)
                }
            }
        }
    }
}