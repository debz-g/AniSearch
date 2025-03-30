package dev.redfox.anisearch.ui.topAnime.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.redfox.anisearch.databinding.ItemAnimeBinding
import dev.redfox.anisearch.models.AnimeChildData
import dev.redfox.anisearch.models.AnimeData
import dev.redfox.anisearch.models.GenericMalItem
import dev.redfox.anisearch.models.OverviewData
import dev.redfox.anisearch.utils.AnimeDiffCallback
import dev.redfox.anisearch.utils.setOnSingleClickListener

class TopAnimeAdapter(
    private val onItemClick: (AnimeChildData, OverviewData) -> Unit
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
                    anime.images.webp.largeImageUrl?.let { largeImageUrl ->
                        onItemClick(
                            AnimeChildData(
                                malId = anime.malId,
                                image = largeImageUrl,
                                title = anime.title,
                                description = anime.synopsis,
                                linkMAL = anime.url,
                                linkTrailer = anime.trailer?.url,
                                genre = extractNames(anime.genres)
                            ),
                            OverviewData(
                                aired = anime.aired.string,
                                duration = anime.duration,
                                episodes = anime.episodes,
                                members = anime.members,
                                rank = anime.rank,
                                favourites = anime.favorites,
                                tags = extractNames(anime.genres)
                            )
                        )
                    }
                }

                animeName.text = anime.title

                animeRating.text = anime.score.toString()

                Glide.with(animePic.context)
                    .load(anime.images.webp.largeImageUrl)
                    .into(animePic)

            }
        }
    }

    fun extractNames(items: List<GenericMalItem>): List<String> {
        return items.mapNotNull { it.name }
    }
}
