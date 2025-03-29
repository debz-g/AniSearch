package dev.redfox.anisearch.ui.animeDetails

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.core.view.WindowCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.redfox.anisearch.databinding.DialogAnimeDetailsFragmentBinding

class AnimeDetailsBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var mContext: Context

    private val binding: DialogAnimeDetailsFragmentBinding by lazy {
        DialogAnimeDetailsFragmentBinding.inflate(layoutInflater)
    }

    companion object {
        fun getInstance(/*animeChildData: AnimeChildData*/) : AnimeDetailsBottomSheetFragment {
            val fragment = AnimeDetailsBottomSheetFragment()
            /*fragment.arguments = Bundle().apply {
                putParcelable(Constants.Companion.PARAM_DATA, animeChildData)
            }*/
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
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
            WindowCompat.setDecorFitsSystemWindows(it.window!!, false)
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
    }
}