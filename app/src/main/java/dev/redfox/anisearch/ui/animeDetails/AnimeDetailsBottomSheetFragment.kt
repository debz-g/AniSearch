package dev.redfox.anisearch.ui.animeDetails

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayout
import dev.redfox.anisearch.R
import dev.redfox.anisearch.databinding.DialogAnimeDetailsFragmentBinding
import dev.redfox.anisearch.databinding.ItemCustomTabBinding
import dev.redfox.anisearch.models.AnimeChildData
import dev.redfox.anisearch.models.OverviewData
import dev.redfox.anisearch.ui.overview.OverviewFragment
import dev.redfox.anisearch.utils.PARAM_DATA
import dev.redfox.anisearch.utils.PARAM_DATA_ALT
import dev.redfox.anisearch.utils.disappear
import dev.redfox.anisearch.utils.getModelView
import dev.redfox.anisearch.utils.hide
import dev.redfox.anisearch.utils.openUrlWithCustomTab
import dev.redfox.anisearch.utils.replaceChildFragment
import dev.redfox.anisearch.utils.setImage
import dev.redfox.anisearch.utils.setOnSelectView
import dev.redfox.anisearch.utils.setOnSingleClickListener
import dev.redfox.anisearch.utils.setUnSelectView
import dev.redfox.anisearch.utils.show
import dev.redfox.anisearch.utils.showToast
import dev.redfox.anisearch.viewmodel.AnimeDetailsViewModel
import kotlin.math.abs

class AnimeDetailsBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var mContext: Context
    private lateinit var animeDetailsVm: AnimeDetailsViewModel

    private val binding: DialogAnimeDetailsFragmentBinding by lazy {
        DialogAnimeDetailsFragmentBinding.inflate(layoutInflater)
    }

    companion object {
        fun getInstance(
            animeChildData: AnimeChildData,
            overviewData: OverviewData
        ): AnimeDetailsBottomSheetFragment {
            val fragment = AnimeDetailsBottomSheetFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(PARAM_DATA, animeChildData)
                putParcelable(PARAM_DATA_ALT, overviewData)
            }
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        animeDetailsVm = getModelView(AnimeDetailsViewModel()) as AnimeDetailsViewModel
    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        dialog?.let {
            val bottomSheet =
                it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val layoutParams = bottomSheet.layoutParams
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
            bottomSheet.layoutParams = layoutParams
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.isDraggable = false
        }
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
        animeDetailsVm.handleExtras(arguments)
        initObservers()
        initClickListeners()
        handleAppBarScroll()
        initTabLayout()
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            animeDetailsVm.getAnimeChildData()?.let { animeData ->
                setImage(
                    mContext,
                    animeData.image,
                    ivAnimeBanner,
                    false
                )
                tvAnimeDetailsDescription.apply {
                    movementMethod = LinkMovementMethod.getInstance()
                    highlightColor = Color.TRANSPARENT
                    isClickable = true
                    text = animeDetailsVm.getTrimmedAbout(mContext)
                }
                tvAnimeDetailsToolbarTitle.text = animeData.title
            }
        }
    }

    private fun handleAppBarScroll() {
        binding.apply {
            ablPublishedContentInfo.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
                val percentage = (abs(verticalOffset).toFloat() / appBarLayout.height)
                if (percentage > .75) {
                    tvAnimeDetailsDescription.disappear()
                    tvAnimeDetailsToolbarTitle.show()
                } else {
                    tvAnimeDetailsToolbarTitle.hide()
                    tvAnimeDetailsDescription.show()
                }
            }
        }
    }

    private fun initTabLayout() {
        val list = animeDetailsVm.getTabList(mContext)
        if (list.isNotEmpty()) {
            binding.tlAnimeDetailsTabs.show()
            list.forEach {
                val tabItemBinding = ItemCustomTabBinding.inflate(LayoutInflater.from(mContext))
                tabItemBinding.tvCustomTabItemText.apply {
                    text = it
                    isAllCaps = true
                }
                val tab = binding.tlAnimeDetailsTabs.newTab()
                tab.customView = tabItemBinding.root
                binding.tlAnimeDetailsTabs.addTab(tab)
            }
            binding.tlAnimeDetailsTabs.addOnTabSelectedListener(object :
                TabLayout.OnTabSelectedListener {

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.position?.let { position ->
                        binding.apply {
                            tlAnimeDetailsTabs.setOnSelectView(mContext, position, false)
                            ablPublishedContentInfo.setExpanded(false, true)
                            when(position){
                               0 -> {
                                   binding.nsvAnimeDetails.setOnScrollChangeListener(null as NestedScrollView.OnScrollChangeListener?)
                                   showFragment(OverviewFragment.getInstance(animeDetailsVm.getOverviewData()))
                               }

                                1 -> {

                                }

                                2 -> {

                                }
                            }
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    tab?.let {
                        binding.tlAnimeDetailsTabs.setUnSelectView(mContext, it.position)
                    }
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    binding.ablPublishedContentInfo.setExpanded(false, true)
                }
            })
            // default loading view
            binding.tlAnimeDetailsTabs.setOnSelectView(mContext, 0, false)
            showFragment(OverviewFragment.getInstance(animeDetailsVm.getOverviewData()))
        } else
            binding.tlAnimeDetailsTabs.hide()
    }

    private fun initObservers() {
        animeDetailsVm.apply {
            descriptionSpanClickObserver.observe(viewLifecycleOwner) { showFullData ->
                binding.tvAnimeDetailsDescription.apply {
                    text = if (showFullData)
                        getFullAbout(mContext)
                    else
                        getTrimmedAbout(mContext)
                }
            }
        }
    }

    private fun initClickListeners() {
        binding.apply {
            mbWatchTrailer.setOnSingleClickListener {
                animeDetailsVm.getTrailerLink()?.let { mContext.openUrlWithCustomTab(it) }
                    ?: mContext.showToast(
                        mContext.getString(
                            R.string.trailer_link_not_found
                        )
                    )
            }

            mbMalLink.setOnSingleClickListener {
                animeDetailsVm.getMalLink()?.let { mContext.openUrlWithCustomTab(it) }
                    ?: mContext.showToast(
                        mContext.getString(
                            R.string.not_listed_on_mal
                        )
                    )
            }
        }
    }


    private fun showFragment(fragment: Fragment) {
        replaceChildFragment(fragment, R.id.fcv_anime_details_container)
    }
}