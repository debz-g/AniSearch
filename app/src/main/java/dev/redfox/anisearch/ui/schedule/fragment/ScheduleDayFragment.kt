package dev.redfox.anisearch.ui.schedule.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dev.redfox.anisearch.databinding.FragmentScheduleDayBinding
import dev.redfox.anisearch.ui.schedule.adapter.ScheduleAdapter
import dev.redfox.anisearch.utils.PARAM_ARG_1
import dev.redfox.anisearch.utils.getModelView
import dev.redfox.anisearch.viewmodel.ScheduleViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ScheduleDayFragment: Fragment() {
    private val binding: FragmentScheduleDayBinding by lazy {
        FragmentScheduleDayBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: ScheduleViewModel
    private lateinit var mContext: Context
    private val scheduleAdapter = ScheduleAdapter()

    companion object {
        fun newInstance(day: String): ScheduleDayFragment {
            val fragment = ScheduleDayFragment()
            fragment.arguments = Bundle().apply {
                putString(PARAM_ARG_1, day)
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

        binding.rvSchedule.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(mContext)
            adapter = scheduleAdapter
        }
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.apply {
                getSchedule(getDay()).collectLatest { pagingData ->
                    scheduleAdapter.submitData(pagingData)
                }
            }
        }
    }
}