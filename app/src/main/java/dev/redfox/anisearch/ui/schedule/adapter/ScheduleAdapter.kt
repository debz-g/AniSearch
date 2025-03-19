package dev.redfox.anisearch.ui.schedule.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.redfox.anisearch.databinding.ItemAnimeBinding
import dev.redfox.anisearch.models.CommonApiDataClass.AnimeData
import dev.redfox.anisearch.ui.schedule.adapter.ScheduleAdapter.ScheduleViewHolder
import dev.redfox.anisearch.utils.AnimeDiffCallback

class ScheduleAdapter : PagingDataAdapter<AnimeData, ScheduleViewHolder>(
    AnimeDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val binding = ItemAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val anime = getItem(position)
        if (anime != null) {
            holder.bind(anime)
        }
    }

    class ScheduleViewHolder(private val binding: ItemAnimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(anime: AnimeData) {

        }
    }
}