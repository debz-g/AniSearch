package dev.redfox.anisearch.utils

import androidx.recyclerview.widget.DiffUtil
import dev.redfox.anisearch.models.AnimeData
import dev.redfox.anisearch.models.CharacterData
import dev.redfox.anisearch.models.Episode

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

class EpisodesDiffCallback() : DiffUtil.ItemCallback<Episode>() {
    override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
        return oldItem.malId == newItem.malId
    }

    override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
        return oldItem == newItem
    }
}

class CharactersDiffCallback() : DiffUtil.ItemCallback<CharacterData>() {
    override fun areItemsTheSame(oldItem: CharacterData, newItem: CharacterData): Boolean {
        return oldItem.character.malId == newItem.character.malId
    }

    override fun areContentsTheSame(oldItem: CharacterData, newItem: CharacterData): Boolean {
        return oldItem == newItem
    }
}