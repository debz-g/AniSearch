package dev.redfox.anisearch.ui.schedule.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.redfox.anisearch.databinding.ItemAnimeBinding
import dev.redfox.anisearch.models.AnimeChildData
import dev.redfox.anisearch.models.AnimeData
import dev.redfox.anisearch.models.OverviewData
import dev.redfox.anisearch.ui.schedule.adapter.ScheduleAdapter.ScheduleViewHolder
import dev.redfox.anisearch.utils.AnimeDiffCallback
import dev.redfox.anisearch.utils.extractNames
import dev.redfox.anisearch.utils.setOnSingleClickListener

class ScheduleAdapter(private val onItemClick: (AnimeChildData, OverviewData) -> Unit) :
    PagingDataAdapter<AnimeData, ScheduleViewHolder>(
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

    inner class ScheduleViewHolder(private val binding: ItemAnimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(anime: AnimeData) {
            binding.apply {
                animeName.text = anime.title
                Glide.with(animePic.context)
                    .load(anime.images.webp.largeImageUrl)
                    .into(animePic)
                itemView.setOnSingleClickListener {
                    anime.images.webp.largeImageUrl?.let { largeImageUrl ->
                        onItemClick(
                            AnimeChildData(
                                malId = anime.malId,
                                image = largeImageUrl,
                                title = anime.title,
                                description = anime.synopsis,
                                linkMAL = anime.url,
                                linkTrailer = anime.trailer?.url,
                                englishTitle = anime.titleEnglish,
                                japaneseTitle = anime.titleJapanese,
                                genre = extractNames(anime.genres)
                            ),
                            OverviewData(
                                malId = anime.malId,
                                aired = anime.aired.string,
                                duration = anime.duration,
                                episodes = anime.episodes,
                                members = anime.members,
                                rank = anime.rank,
                                favourites = anime.favorites,
                                tags = extractNames(anime.genres),
                                producers = extractNames(anime.producers),
                                studio = anime.studios?.firstOrNull()?.name
                            )
                        )
                    }
                }
            }
        }
    }
}