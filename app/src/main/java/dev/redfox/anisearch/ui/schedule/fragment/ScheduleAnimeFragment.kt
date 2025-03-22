package dev.redfox.anisearch.ui.schedule.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import dev.redfox.anisearch.databinding.FragmentScheduleAnimeBinding
import dev.redfox.anisearch.ui.schedule.adapter.ScheduleTabAdapter
import dev.redfox.anisearch.utils.getModelView
import dev.redfox.anisearch.viewmodel.ScheduleViewModel

class ScheduleAnimeFragment : Fragment() {
    private val binding: FragmentScheduleAnimeBinding by lazy {
        FragmentScheduleAnimeBinding.inflate(layoutInflater)
    }

    private lateinit var mContext: Context
    private lateinit var viewModel: ScheduleViewModel
    private lateinit var scheduleTabAdapter: ScheduleTabAdapter

    private val daysOfWeek =
        listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

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
        setStatusBarInsets()
        scheduleTabAdapter = ScheduleTabAdapter(this)
        binding.viewPager.adapter = scheduleTabAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = daysOfWeek[position]
        }.attach()
    }

    private fun setStatusBarInsets() {
        binding.root.apply {
            ViewCompat.setOnApplyWindowInsetsListener(this) { v, windowInsets ->
                val statusBarHeight =
                    windowInsets.getInsets(WindowInsetsCompat.Type.statusBars()).top
                binding.scheduleStatusBarPlaceholder.apply {
                    layoutParams = layoutParams.apply { height = statusBarHeight }
                }
                windowInsets
            }
        }
    }
}