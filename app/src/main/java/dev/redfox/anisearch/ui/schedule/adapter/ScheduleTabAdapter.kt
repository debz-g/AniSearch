package dev.redfox.anisearch.ui.schedule.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.redfox.anisearch.ui.schedule.fragment.ScheduleDayFragment

class ScheduleTabAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val daysOfWeek = listOf("monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday")

    override fun getItemCount(): Int = daysOfWeek.size

    override fun createFragment(position: Int): Fragment {
        return ScheduleDayFragment.newInstance(daysOfWeek[position])
    }
}