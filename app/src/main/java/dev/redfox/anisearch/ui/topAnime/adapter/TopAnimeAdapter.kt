package dev.redfox.anisearch.ui.topAnime.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.redfox.anisearch.databinding.ItemAnimeBinding
import dev.redfox.anisearch.models.TopApiDataClass.AnimeData

class TopAnimeAdapter : PagingDataAdapter<AnimeData, TopAnimeAdapter.AnimeViewHolder>(
    AnimeDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val binding = ItemAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val anime = getItem(position)
        if (anime != null) {
            holder.bind(anime)
        }
    }

    class AnimeViewHolder(private val binding: ItemAnimeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(anime: AnimeData) {
            binding.textViewTitle.text = anime.title
            Glide.with(binding.imageView.context)
                .load(anime.images.jpg.imageUrl)
                .into(binding.imageView)
        }
    }

    class AnimeDiffCallback : DiffUtil.ItemCallback<AnimeData>() {
        override fun areItemsTheSame(oldItem: AnimeData, newItem: AnimeData): Boolean = oldItem.malId == newItem.malId
        override fun areContentsTheSame(oldItem: AnimeData, newItem: AnimeData): Boolean = oldItem == newItem
    }
}
