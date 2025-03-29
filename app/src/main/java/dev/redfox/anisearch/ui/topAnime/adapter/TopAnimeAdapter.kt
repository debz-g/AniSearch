package dev.redfox.anisearch.ui.topAnime.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.redfox.anisearch.databinding.ItemAnimeBinding
import dev.redfox.anisearch.models.AnimeData
import dev.redfox.anisearch.utils.AnimeDiffCallback
import dev.redfox.anisearch.utils.setOnSingleClickListener

class TopAnimeAdapter(
    private val onItemClick: (Int) -> Unit
) : PagingDataAdapter<AnimeData, TopAnimeAdapter.AnimeViewHolder>(
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

    inner class AnimeViewHolder(private val binding: ItemAnimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(anime: AnimeData) {
            binding.apply {
                itemView.setOnSingleClickListener {
                    onItemClick(0)
                }

                animeName.text = anime.title

                animeRating.text = anime.score.toString()

                Glide.with(animePic.context)
                    .load(anime.images.webp.largeImageUrl)
                    .into(animePic)

            }
        }
    }
}
