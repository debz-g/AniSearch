package dev.redfox.anisearch.utils

import androidx.recyclerview.widget.DiffUtil
import dev.redfox.anisearch.models.AnimeData

class AnimeDiffCallback() : DiffUtil.ItemCallback<AnimeData>() {
    override fun areItemsTheSame(oldItem: AnimeData, newItem: AnimeData): Boolean =
        oldItem.malId == newItem.malId

    override fun areContentsTheSame(oldItem: AnimeData, newItem: AnimeData): Boolean =
        oldItem == newItem
}

class StringListDiffCallback(
    private val oldList: List<String>,
    private val newList: List<String>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return oldList[oldPosition] == newList[newPosition]
    }
}