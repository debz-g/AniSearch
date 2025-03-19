package dev.redfox.anisearch.utils

import androidx.recyclerview.widget.DiffUtil
import dev.redfox.anisearch.models.CommonApiDataClass.AnimeData

class AnimeDiffCallback() : DiffUtil.ItemCallback<AnimeData>() {
    override fun areItemsTheSame(oldItem: AnimeData, newItem: AnimeData): Boolean =
        oldItem.malId == newItem.malId

    override fun areContentsTheSame(oldItem: AnimeData, newItem: AnimeData): Boolean =
        oldItem == newItem
}