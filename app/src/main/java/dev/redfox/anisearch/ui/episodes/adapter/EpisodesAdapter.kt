package dev.redfox.anisearch.ui.episodes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.redfox.anisearch.R
import dev.redfox.anisearch.databinding.ItemEpisodesBinding
import dev.redfox.anisearch.models.Episode
import dev.redfox.anisearch.ui.episodes.adapter.EpisodesAdapter.EpisodeViewHolder
import dev.redfox.anisearch.utils.EpisodesDiffCallback
import dev.redfox.anisearch.utils.formatAiredDate

class EpisodesAdapter(private val mmContext: Context) : PagingDataAdapter<Episode, EpisodeViewHolder>(
    EpisodesDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val binding = ItemEpisodesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EpisodeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episode = getItem(position)
        if (episode != null) {
            holder.bind(episode)
        }
    }

    inner class EpisodeViewHolder(
        private val binding: ItemEpisodesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(episode: Episode) {
            binding.apply {
                tvEpisodeNumber.text = mmContext.getString(R.string.episode_number, episode.malId)
                tvEpisodeTitle.text = episode.title
                episode.aired?.let { airedDate ->
                    tvEpisodeDate.text = formatAiredDate(airedDate)
                }
            }
        }
    }
}