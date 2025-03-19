package dev.redfox.anisearch.ui.schedule.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dev.redfox.anisearch.databinding.FragmentScheduleAnimeBinding
import dev.redfox.anisearch.network.AnimeRepository
import dev.redfox.anisearch.network.RetrofitClient
import dev.redfox.anisearch.ui.schedule.adapter.ScheduleAdapter
import dev.redfox.anisearch.ui.schedule.adapter.ScheduleTabAdapter
import dev.redfox.anisearch.ui.topAnime.adapter.TopAnimeAdapter
import dev.redfox.anisearch.utils.getModelView
import dev.redfox.anisearch.viewmodel.ScheduleViewModel
import dev.redfox.anisearch.viewmodel.TopAnimeViewModel

class ScheduleAnimeFragment : Fragment() {
    private val binding: FragmentScheduleAnimeBinding by lazy {
        FragmentScheduleAnimeBinding.inflate(layoutInflater)
    }

    private lateinit var mContext: Context
    private lateinit var viewModel: ScheduleViewModel
    private val scheduleTabAdapter = ScheduleTabAdapter(this)

    private val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

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

        binding.viewPager.adapter = scheduleTabAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = daysOfWeek[position]
        }.attach()
    }
}