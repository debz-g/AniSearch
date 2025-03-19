package dev.redfox.anisearch.ui.schedule.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.redfox.anisearch.databinding.FragmentScheduleAnimeBinding
import dev.redfox.anisearch.utils.Constants
import dev.redfox.anisearch.utils.getModelView
import dev.redfox.anisearch.viewmodel.ScheduleViewModel

class ScheduleDayFragment: Fragment() {
    private val binding: FragmentScheduleAnimeBinding by lazy {
        FragmentScheduleAnimeBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: ScheduleViewModel
    private lateinit var mContext: Context

    companion object {
        fun newInstance(day: String): ScheduleDayFragment {
            val fragment = ScheduleDayFragment()
            fragment.arguments = Bundle().apply {
                putString(Constants.PARAM_ARG_1, day)
            }
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        viewModel = getModelView(ScheduleViewModel()) as ScheduleViewModel
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
        viewModel.handleExtras(arguments)


    }
}