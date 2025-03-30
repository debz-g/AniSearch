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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.redfox.anisearch.R
import dev.redfox.anisearch.databinding.DialogAnimeDetailsFragmentBinding
import dev.redfox.anisearch.models.AnimeChildData
import dev.redfox.anisearch.utils.PARAM_DATA
import dev.redfox.anisearch.utils.getModelView
import dev.redfox.anisearch.utils.openUrlWithCustomTab
import dev.redfox.anisearch.utils.setImage
import dev.redfox.anisearch.utils.setOnSingleClickListener
import dev.redfox.anisearch.utils.showToast
import dev.redfox.anisearch.viewmodel.AnimeDetailsViewModel

class AnimeDetailsBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var mContext: Context
    private lateinit var animeDetailsVm: AnimeDetailsViewModel

    private val binding: DialogAnimeDetailsFragmentBinding by lazy {
        DialogAnimeDetailsFragmentBinding.inflate(layoutInflater)
    }

    companion object {
        fun getInstance(animeChildData: AnimeChildData): AnimeDetailsBottomSheetFragment {
            val fragment = AnimeDetailsBottomSheetFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(PARAM_DATA, animeChildData)
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
        setupUI()
        initObservers()
        initClickListeners()
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
                animeDetailsVm.getTrailerLink()?.let { mContext.openUrlWithCustomTab(it) } ?: mContext.showToast(
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
}