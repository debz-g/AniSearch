package dev.redfox.anisearch.ui.overview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import dev.redfox.anisearch.databinding.FragmentOverviewBinding
import dev.redfox.anisearch.models.OverviewData
import dev.redfox.anisearch.ui.overview.adapter.GenresAdapter
import dev.redfox.anisearch.ui.overview.adapter.ProducersAdapter
import dev.redfox.anisearch.utils.PARAM_DATA
import dev.redfox.anisearch.utils.getModelView
import dev.redfox.anisearch.utils.hide
import dev.redfox.anisearch.utils.show
import dev.redfox.anisearch.viewmodel.OverviewViewModel
import java.text.NumberFormat
import java.util.Locale

class OverviewFragment : Fragment() {

    private val binding: FragmentOverviewBinding by lazy {
        FragmentOverviewBinding.inflate(layoutInflater)
    }
    private lateinit var mContext: Context
    private lateinit var overviewVm: OverviewViewModel
    private lateinit var genresAdapter: GenresAdapter
    private lateinit var producersAdapter: ProducersAdapter

    companion object {
        fun getInstance(overviewData: OverviewData?): OverviewFragment {
            val fragment = OverviewFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(PARAM_DATA, overviewData)
            }
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        overviewVm = getModelView(OverviewViewModel()) as OverviewViewModel
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
        overviewVm.handleExtras(arguments)
        setupInfoCards()
        initTagsAdapter()
    }

    private fun initTagsAdapter() {
        binding.apply {
            genresAdapter = GenresAdapter(mContext)
            rvOverviewGenre.apply {
                show()
                setHasFixedSize(true)
                layoutManager =
                    LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                adapter = genresAdapter
            }
            overviewVm.getTags()?.let { genresAdapter.updateData(it.take(3)) }
                ?: run {
                    rvOverviewGenre.hide()
                    tvOverviewGenres.hide()
                }
            overviewVm.getStudio()?.let { itemTagProducer.tvTagItemText.text = it }
                ?: run {
                    itemTagProducer.root.hide()
                    tvOverviewStudio.hide()
                }
            producersAdapter = ProducersAdapter(mContext)
            rvOverviewProducers.apply {
                show()
                tvOverviewProducers.show()
                setHasFixedSize(true)
                layoutManager =
                    LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                adapter = producersAdapter
            }
            overviewVm.getProducers()?.let {
                if (it.isNotEmpty())
                    producersAdapter.updateData(it.take(3))
                else {
                    tvOverviewProducers.hide()
                    rvOverviewProducers.hide()
                }
            }
                ?: run {
                    tvOverviewProducers.hide()
                    rvOverviewProducers.hide()
                }
        }
    }

    private fun setupInfoCards() {
        val overviewData = overviewVm.getOverviewData() ?: return
        with(binding) {
            overviewData.aired?.let { cardAired.setSecondaryText(it) }
            overviewData.duration?.let { cardDuration.setSecondaryText(it) }
            cardEpisodes.setSecondaryText(overviewData.episodes.toString())
            cardMembers.setSecondaryText(
                NumberFormat.getNumberInstance(Locale.US)
                    .format(overviewData.members)
            )
            cardRank.setSecondaryText("#${overviewData.rank}")
            cardFavorites.setSecondaryText(
                NumberFormat.getNumberInstance(Locale.US)
                    .format(overviewData.favourites)
            )
        }
    }
}